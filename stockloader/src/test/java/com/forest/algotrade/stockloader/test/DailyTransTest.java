package com.forest.algotrade.stockloader.test;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 15-8-1.
 */
public class DailyTransTest extends CamelTestSupport {

    @Test
    public void testUnmarshalBindy() throws Exception {

        // when the first exchange is done
        NotifyBuilder notify = new NotifyBuilder(context).whenDoneByIndex(0).create();

        long start = System.currentTimeMillis();

        System.out.println("Waiting to be done with 2 min timeout (use ctrl + c to stop)");
        notify.matches(2 * 60, TimeUnit.SECONDS);

        long delta = System.currentTimeMillis() - start;
        System.out.println("Took " + delta / 1000 + " seconds");

     //        ProducerTemplate template = context.createProducerTemplate();
//        template.sendBody("direct:toObject", "Camel in Action,39.95,1");
//
//        mock.assertIsSatisfied();


        /*
        // bindy now turned that into a list of rows so we need to grab the order from the list
        List rows = mock.getReceivedExchanges().get(0).getIn().getBody(List.class);
        // each row is a map with the class name as the index
        Map row = (Map) rows.get(0);
        // get the order from the map
        DailyTransaction order = (DailyTransaction) row.get(DailyTransaction.class.getName());
        assertNotNull(order);
        */

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                //from("direct:toObject")
                // .bindy(BindyType.Csv, "com.forest.stock.bean")

                /*
                from("file://data/inbox?noop=true&maxMessagesPerPoll=1&delay=5000")
                        .split(body().tokenize("\n")).streaming()
                        .unmarshal().bindy(BindyType.Csv, "com.ess.myapp.core")
                        .to("jms:rawTraffic");
                        &fileName=testdata.csv

                from("file:///home/dev/testdata?noop=true&maxMessagesPerPoll=100&delay=2000")
                        .log("Starting to process big file: ${header.CamelFileName}")
                        .split(body().tokenize("\n")).streaming()
                        .unmarshal().bindy(BindyType.Csv, "com.forest.stock.bean")
                        .to("mock:result");

                from("file:///home/dev/testdata?noop=true")
                        .log("Starting to process big file: ${header.CamelFileName}")
                                // by enabling the parallel processing the output from the split EIP will
                                // be processed concurrently using the default thread pool settings
                                // which is a grow/shrink pool between 10-20 threads
                                // inactive threads will terminate after 60 seconds
                        .split(body().tokenize("\n")).streaming().parallelProcessing()
                        .unmarshal().bindy(BindyType.Csv, "com.forest.stock.bean")
                        .to("direct:update");
                    */
//                from("direct:update")
//                        .bean(CsvToDailyTransactionBean.class, "updateInventory");
            }
        };
    }
}
