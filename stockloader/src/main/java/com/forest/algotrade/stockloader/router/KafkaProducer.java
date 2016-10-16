package com.forest.algotrade.stockloader.router;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 16-10-16.
 */
public class KafkaProducer extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        /* doesn't work
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
        */

        // work!
        /*
        from("timer://foo?fixedRate=true&period=3000")
                .setBody(constant("Hello message from apache camel"))
                .to("kafka:localhost:9092?topic=test");
        */

        // work!
        from("timer://foo?fixedRate=true&period=3000").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
              // single message
                /*
                exchange.getIn().setBody("GOOD DATE",String.class);
                exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, 0);
                exchange.getIn().setHeader(KafkaConstants.KEY, "3");
                */

                // multiple message
                List<Serializable> list = new ArrayList<Serializable>();
                for (int i = 0; i < 100; i++) {
                    list.add("This my dummy message #" + i);
                }
                exchange.getIn().setBody(list);

            }
        }) .to("kafka:localhost:9092?topic=test");
    }
}
