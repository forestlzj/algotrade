package com.forest.algotrade.stockloader.router;

import org.apache.camel.builder.RouteBuilder;


/**
 * Created by dev on 16-10-16.
 */
public class SimpleRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://helloTimer?period=3000").
                to("log:" + "Test Message" + getClass().getName());
    }
}
