package com.forest.algotrade.stockloader.router;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;

/**
 * Created by dev on 16-10-16.
 */
public class KafkaProducer extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:start").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("GOOD",String.class);
                exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, 0);
                exchange.getIn().setHeader(KafkaConstants.KEY, "3");
                //exchange.getIn().setHeader(KafkaConstants.TOPIC, "test");
               // exchange.getIn().setHeader(KafkaConstants.KAFKA_DEFAULT_DESERIALIZER, "org.apache.kafka.common.serialization.StringSerializer");
            }
        }).to("kafka:localhost:9092?topic=test");
    }
}
