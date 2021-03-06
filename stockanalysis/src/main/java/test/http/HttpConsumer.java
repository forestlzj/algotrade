package test.http;

import akka.actor.ActorRef;
import akka.camel.javaapi.UntypedConsumerActor;

public class HttpConsumer extends UntypedConsumerActor {

  private ActorRef producer;

  public HttpConsumer(ActorRef producer) {
    this.producer = producer;
  }

  public String getEndpointUri() {
    return "jetty:http://0.0.0.0:8875/";
  }

  public void onReceive(Object message) {
    producer.forward(message, getContext());
  }
}
