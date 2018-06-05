package com.forest.algotrade.stockanalysis.scala

import akka.actor.{ActorSystem, Props, ActorRef, Actor}
import akka.camel.{CamelMessage, Consumer}
import akka.routing.{RoundRobinRoutingLogic, ActorRefRoutee, Router}

class StockPECalcRounter extends Actor with Consumer{
  var router = {
    val routees = Vector.fill(5) {
      val r = context.actorOf(Props[StockPECalcWorker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive: Receive = {
    case cm: CamelMessage =>
      router.route(cm, sender())
    case m: Any => unhandled(m)
  }


  override def endpointUri: String = "kafka:localhost:9092?topic=test&groupId=g5&autoOffsetReset=earliest&consumersCount=1"

}
