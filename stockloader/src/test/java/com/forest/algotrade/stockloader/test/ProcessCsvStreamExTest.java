package com.forest.algotrade.stockloader.test;

import com.forest.algotrade.stockloader.transformation.CsvToDailyTransactionBean;
import org.apache.camel.Predicate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 15-8-2.
 */
public class ProcessCsvStreamExTest extends CamelTestSupport {


    private ExecutorService threadPool;

    @Before
    @Override
    public void setUp() throws Exception {
        // must create the custom thread pool before Camel is starting
       // threadPool = Executors.newFixedThreadPool(20);
        threadPool = Executors.newCachedThreadPool();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        // proper cleanup by shutting down the thread pool
        threadPool.shutdownNow();
        super.tearDown();
    }

    @Test
    public void testBigFile() throws Exception {
        // when the first exchange is done
        NotifyBuilder notify = new NotifyBuilder(context).whenDoneByIndex(0).create();

        long start = System.currentTimeMillis();

        System.out.println("Waiting to be done with 2 min timeout (use ctrl + c to stop)");
        notify.matches(10 * 60, TimeUnit.SECONDS);

        long delta = System.currentTimeMillis() - start;
        System.out.println("Took " + delta / 1000 + " seconds");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                Predicate csvHeader = body().startsWith("code");
                Predicate invalidCsvData = body().endsWith(",");
                Predicate validCsvData = PredicateBuilder.and(PredicateBuilder.not(csvHeader),
                        PredicateBuilder.not(invalidCsvData));

                from("file:///home/dev/testdata?noop=true")
                        .log("Starting to process big file: ${header.CamelFileName}")
                                // by enabling the parallel processing the output from the split EIP will
                                // be processed concurrently using the default thread pool settings
                                // which is a grow/shrink pool between 10-20 threads
                                // inactive threads will terminate after 60 seconds
                        //.split(body().tokenize("\n")).streaming().parallelProcessing()

                        // use a custom thread pool so the output from the split EIP will
                        // be processed concurrently
                        //.split(body().tokenize("\n")).streaming().executorService(threadPool)
                        .split(body().tokenize("\n")).streaming().parallelProcessing()
                            .choice()
                                .when(validCsvData)
                                    .bean(CsvToDailyTransactionBean.class, "csvToObject")
                                    .to("direct:update")
                                .otherwise()
                                    .to("direct:ignoredRecord")
                                .endChoice()
                            .end()
                        .end()
                        .log("Done processing big file: ${header.CamelFileName}");

                from("direct:update")
                        .bean(CsvToDailyTransactionBean.class, "updateInventory").end();

                from("direct:ignoredRecord").log("ignoredRecord").end();
            }
        };
    }
}
