package com.forest.algotrade.stockloader.scala

import org.apache.camel.scala.dsl.builder.RouteBuilder

class CsvParallelLoader extends RouteBuilder {
  //todo verify and test
   def configure(): Unit = {
    "file:C:/_nCWD_/app/data/input?noop=true?delay=3000"
    startupOrder(1)
    log("start to process file: ${header.CamelFileName}")
    //spel(body() tokenize ?)
    to("kafka:localhost:9092?topic=test")

  }
}
