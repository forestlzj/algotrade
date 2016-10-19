package com.forest.algotrade.stockloader.test;

import org.apache.camel.builder.RouteBuilder;

/**
 * Route that demonstrates ho multiple onCompletion elements will not execute as expected - only the one defined last will be used.
 */
public class OnCompletionMultipleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onCompletion().onCompleteOnly()
                .log("global onCompletionOnly thread: ${threadName}")
                .to("mock:globalCompleted");
        onCompletion().onFailureOnly()
                .log("global onFailureOnly thread: ${threadName}")
                .to("mock:globalFailed");

        from("direct:in")
                .onCompletion().onCompleteOnly()
                .log("onCompletionOnly triggered: ${threadName}")
                .to("mock:completed")
                .end()
                .onCompletion().onFailureOnly()
                .log("onFailureOnly thread: ${threadName}")
                .to("mock:failed")
                .end()
                .log("Original thread: ${threadName}")
                .choice()
                .when(simple("${body} contains 'explode'"))
                .throwException(new IllegalArgumentException("Exchange caused explosion"))
                .endChoice();

        from("direct:global")
                .log("Original thread: ${threadName}")
                .choice()
                .when(simple("${body} contains 'explode'"))
                .throwException(new IllegalArgumentException("Exchange caused explosion"))
                .endChoice();
    }
}