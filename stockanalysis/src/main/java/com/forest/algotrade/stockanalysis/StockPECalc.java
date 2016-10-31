package com.forest.algotrade.stockanalysis;

import akka.camel.javaapi.UntypedConsumerActor;

/**
 * Created by dev on 16-10-29.
 */
public class StockPECalc  extends UntypedConsumerActor {

    @Override
    public String getEndpointUri() {
        return null;
    }

    @Override
    public void onReceive(Object message) throws Throwable {

    }
}
