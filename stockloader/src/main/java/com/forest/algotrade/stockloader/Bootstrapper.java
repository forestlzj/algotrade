package com.forest.algotrade.stockloader;

import com.forest.algotrade.stockloader.test.KafkaConsumer;
import com.forest.algotrade.stockloader.test.OnCompletionMultipleRouteBuilder;
import com.forest.algotrade.stockloader.test.OnCompletionRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by dev on 16-10-16.
 */
public class Bootstrapper {

    public static void main(String[] args) throws Exception {
        CamelRunner runner = new CamelRunner();
        args = new String[1];
        //args[0] = "com.forest.algotrade.stockloader.router.CsvParallelLoader";
        args[0] = "com.forest.algotrade.stockloader.test.OnCompletionMultipleRouteBuilder";
        runner.run(args);
    }

    private static Logger logger = LoggerFactory.getLogger(CamelRunner.class);
    public void run(String[] args) throws Exception {
        runWithCamelRoutes(args);
        // Wait for user to hit CRTL+C to stop the service
        synchronized(this) {
            this.wait();
        }
    }


    private void runWithCamelRoutes(String[] args) throws Exception {
        final CamelContext camelTestContext = new DefaultCamelContext();
        // Register proper shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    camelTestContext.stop();
                    logger.info("Camel stopped for {}", camelTestContext);
                } catch (Exception e) {
                    logger.error("Failed to stop Camel.", e);
                }
            }
        });

        // Added RouteBuilder from args
        for (String className : args) {
            Class<?> cls = Class.forName(className);
            if (RouteBuilder.class.isAssignableFrom(cls)) {
                Object obj = cls.newInstance();
                RouteBuilder routeBuilder = (RouteBuilder)obj;
                camelTestContext.addRoutes(routeBuilder);
            } else {
                throw new RuntimeException("Unable to add Camel RouteBuilder " + className);
            }
        }

        // Start camel
        camelTestContext.start();
        logger.info("Camel started for {}", camelTestContext);
    }
}
