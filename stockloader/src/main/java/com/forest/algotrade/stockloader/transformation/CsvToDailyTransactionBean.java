package com.forest.algotrade.stockloader.transformation;

import com.forest.algotrade.stockloader.bean.DailyTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dev on 15-8-2.
 */
public class CsvToDailyTransactionBean {

    private static final Logger LOG = LoggerFactory.getLogger(CsvToDailyTransactionBean.class);

    /**
     * Convert the CSV to a model object
     */
    public DailyTransaction csvToObject(String csv) {
        String[] lines = csv.split(",");
        if (lines == null || lines.length != 19) {
            LOG.warn("Line# " + lines.length + " " + csv) ;
             throw new IllegalArgumentException("CSV line is not valid: " + csv);
           // LOG.warn("CSV line is not valid: " + csv);
           // return null; <--- NoTypeConversionAvailableException
        }

        String stockcode = lines[0];
        String date = lines[1];
        String marketValue = lines[10];
        String pb = lines[18];
        return new DailyTransaction(stockcode, date, marketValue, pb);
    }

    /**
     * To simulate updating the inventory by calling some external system which takes a bit of time
     */
    public void updateInventory(DailyTransaction update) throws Exception {
        // simulate updating using some CPU processing
        Thread.sleep(100);

        LOG.info("Stock " + update + " updated");
    }
}
