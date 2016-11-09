package com.forest.algotrade.stockanalysis;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import test.http.HttpConsumer;
import test.http.HttpProducer;
import test.http.HttpTransformer;

public class ActorBooter {
  public static void main(String[] args) {
     ActorSystem system = ActorSystem.create("stockanalysis-system");
     system.actorOf(Props.create(StockPECalcRouter.class));
  }
}
