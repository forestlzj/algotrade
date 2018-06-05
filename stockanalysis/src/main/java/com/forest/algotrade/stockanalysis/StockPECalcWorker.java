package com.forest.algotrade.stockanalysis;

import akka.actor.UntypedActor;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.CamelMessage;

/**
 * Created by dev on 16-10-29.
 */

// http://doc.akka.io/docs/akka/snapshot/scala/routing.html
// http://doc.akka.io/docs/akka/snapshot/java/routing.html
public class StockPECalcWorker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof CamelMessage) {
            CamelMessage camelMessage = (CamelMessage) message;
            System.out.println("camelMsg:" + camelMessage.body());
        }
    }
}
