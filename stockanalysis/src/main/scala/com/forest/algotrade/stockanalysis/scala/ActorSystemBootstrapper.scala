package com.forest.algotrade.stockanalysis.scala

import akka.actor.{Props, ActorSystem}
import akka.camel.CamelExtension

object ActorSystemBootstrapper {

  def main (args: Array[String]){
    val system = ActorSystem("rounter-system")
    val stockRouter = system.actorOf(Props[StockPECalcRounter])
   // CamelExtension(system).context.addRoutes(new CustomerRouterBuilder)
  }

}
