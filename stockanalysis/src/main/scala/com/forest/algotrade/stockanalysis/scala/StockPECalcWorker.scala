package com.forest.algotrade.stockanalysis.scala

import akka.actor.Actor
import akka.camel.CamelMessage


class StockPECalcWorker extends Actor {
  override def receive: Receive = {
    case msg: CamelMessage => println(msg)
  }
}
