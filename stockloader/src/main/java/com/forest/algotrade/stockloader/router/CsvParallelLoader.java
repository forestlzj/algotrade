package com.forest.algotrade.stockloader.router;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

import java.io.IOException;

/**
 * Created by pdclzj on 17/10/2016.
 */
public class CsvParallelLoader extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        //todo retry will cause duplicate data, need to de-dup in backend

        //  default error(uncaught exception) handler which is context scoped
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(2)
                .redeliveryDelay(5000)
                .retryAttemptedLogLevel(LoggingLevel.WARN));

        // exception handler for specific exceptions
        onException(IOException.class).maximumRedeliveries(1).redeliveryDelay(5000);
        //onException(Exception.class).maximumRedeliveries(1).redeliveryDelay(10000);

        //only the failed record send to kafka and write to error folder
        onException(CsvRecordException.class)
                .to("kafka:localhost:9092?topic=test-dlc")
                .to("file:/app/dev/dataland/error");

        onCompletion()
                .log("global thread: ${threadName}")
                .to("file:/app/dev/dataland/archive");

        from("file:/app/dev/dataland/input?noop=true?delay=3000")
                //.onCompletion().to("file:/app/dev/dataland/archive").end()
                .log("start to process file: ${header.CamelFileName}")
                .bean(CsvFilePreLoadChecker.class, "validateMetaData")
                .split(body().tokenize("\n")).streaming().parallelProcessing()
                      .bean(CsvFilePreLoadChecker.class, "toCsvLine")
                      .process(new Processor() {
                          @Override
                          public void process(Exchange exchange) throws Exception {
                              String pKey = exchange.getIn().getBody().toString().split(",")[0];
                              exchange.getIn().setHeader(KafkaConstants.KEY, pKey);
                          }
                      })
               .to("kafka:localhost:9092?topic=test")
                .end()
                .log("Done processing file: ${header.CamelFileName}");

    }
}
