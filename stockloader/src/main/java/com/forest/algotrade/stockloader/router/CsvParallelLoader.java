package com.forest.algotrade.stockloader.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

/**
 * Created by pdclzj on 17/10/2016.
 */
public class CsvParallelLoader extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:C:/_nCWD_/data?noop=true")
                .log("start to process file: ${header.CamelFileName}")
                .split(body().tokenize("\n")).streaming().parallelProcessing()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String pKey = exchange.getIn().getBody().toString().split(",")[0];
                        exchange.getIn().setHeader(KafkaConstants.KEY, pKey);
                    }
                })
        //.to("direct:update")
               .to("kafka:hkl103276.hk.hsbc:9092?topic=test")
                .end()
                .log("Done processing big file: ${header.CamelFileName}");

        // to console
        //from("direct:update").to("stream:out");
        // to kafka
        //from("direct:update").to("kafka:hkl103276.hk.hsbc:9092?topic=test");

    }
}
