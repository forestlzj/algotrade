package com.forest.algotrade.stockloader.test;

import com.forest.algotrade.stockloader.transformation.CsvToDailyTransactionBean;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 15-8-2.
 */
public class ProcessCsvStreamTest  extends CamelTestSupport {

    @Test
    public void testBigFile() throws Exception {
        // when the first exchange is done
        NotifyBuilder notify = new NotifyBuilder(context).whenDoneByIndex(0).create();

        long start = System.currentTimeMillis();

        System.out.println("Waiting to be done with 2 min timeout (use ctrl + c to stop)");
        notify.matches(2 * 60, TimeUnit.SECONDS);

        long delta = System.currentTimeMillis() - start;
        System.out.println("Took " + delta / 1000 + " seconds");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:///home/dev/testdata?noop=true")
                        .log("Starting to process big file: ${header.CamelFileName}")
                                // by enabling the parallel processing the output from the split EIP will
                                // be processed concurrently using the default thread pool settings
                                // which is a grow/shrink pool between 10-20 threads
                                // inactive threads will terminate after 60 seconds
                        .split(body().tokenize("\n")).streaming().parallelProcessing()
                        .bean(CsvToDailyTransactionBean.class, "csvToObject")
                        .to("direct:update")
                        .end()
                        .log("Done processing big file: ${header.CamelFileName}");

                from("direct:update")
                        .bean(CsvToDailyTransactionBean.class, "updateInventory");
            }
        };
    }
}
