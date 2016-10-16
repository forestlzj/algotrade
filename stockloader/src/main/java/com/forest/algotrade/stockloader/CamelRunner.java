package com.forest.algotrade.stockloader;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by dev on 16-10-16.
 */
public class CamelRunner {
    public static void main(String[] args) throws Exception {
        CamelRunner runner = new CamelRunner();
        runner.run(args);
    }

    private static Logger logger = LoggerFactory.getLogger(CamelRunner.class);
    public void run(String[] args) throws Exception {
        if (Boolean.parseBoolean(System.getProperty("spring", "false")))
            runWithSpringConfig(args);
        else
            runWithCamelRoutes(args);

        // Wait for user to hit CRTL+C to stop the service
        synchronized(this) {
            this.wait();
        }
    }

    private void runWithSpringConfig(String[] args) {
        final ConfigurableApplicationContext springContext = new FileSystemXmlApplicationContext(args);

        // Register proper shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    springContext.close();
                    logger.info("Spring stopped.");
                } catch (Exception e) {
                    logger.error("Failed to stop Spring.", e);
                }
            }
        });

        // Start spring
        logger.info("Spring started.");
    }

    private void runWithCamelRoutes(String[] args) throws Exception {
        final CamelContext camelContext = new DefaultCamelContext();
        // Register proper shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    camelContext.stop();
                    logger.info("Camel stopped for {}", camelContext);
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
                camelContext.addRoutes(routeBuilder);
            } else {
                throw new RuntimeException("Unable to add Camel RouteBuilder " + className);
            }
        }

        // Start camel
        camelContext.start();
        logger.info("Camel started for {}", camelContext);
    }
}