package test.route;

import akka.camel.javaapi.UntypedProducerActor;

public class RouteProducer extends UntypedProducerActor {
  public String getEndpointUri() {
    return "direct:welcome";
  }
}
