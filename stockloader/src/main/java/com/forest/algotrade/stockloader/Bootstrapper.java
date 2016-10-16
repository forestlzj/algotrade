package com.forest.algotrade.stockloader;

import com.forest.algotrade.stockloader.router.KafkaProducer;
import com.forest.algotrade.stockloader.router.SimpleRouter;
import com.forest.algotrade.stockloader.test.KafkaConsumer;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by dev on 16-10-16.
 */
public class Bootstrapper {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new KafkaConsumer());
        //ProducerTemplate template = context.createProducerTemplate();
        context.start();
       // Thread.sleep(1000);
       // context.stop();
    }
}
