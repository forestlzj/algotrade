package com.forest.algotrade.stockanalysis;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.dispatch.Mapper;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 16-10-29.
 */

// http://doc.akka.io/docs/akka/snapshot/scala/routing.html
// http://doc.akka.io/docs/akka/snapshot/java/routing.html
public class StockPECalcRouter extends UntypedConsumerActor {

    Router router;
    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(StockPECalcWorker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof CamelMessage) {
            CamelMessage camelMessage = (CamelMessage) message;
            router.route(camelMessage, getSender());
        } else if (message instanceof Status.Failure) {
            // ??
            getSender().tell(message, getSelf());
        } else {
            unhandled(message);
        }
    }

    @Override
    public String getEndpointUri() {
        return "kafka:localhost:9092?topic=test&groupId=g1&autoOffsetReset=earliest&consumersCount=1";
    }
}
