package com.forest.algotrade.stockloader.router;

import org.apache.camel.Exchange;

/**
 * Created by pdclzj on 19/10/2016.
 */
public class CsvFilePreLoadChecker {


    public void validateMetaData(Exchange exchange) throws CsvFileFormatException
    {
        //Todo validate the csv file with the defined metadata
       // throw new CsvFileFormatException("invalid csv file format, please refer metadata id: xx");
    }

    public String toCsvLine(String body) throws CsvRecordException
    {
        //Todo convert a csvline by the defined metadata
        //if(body.contains("sz300432")) throw  new CsvRecordException("sz300432 will be ignored");
        return body;
    }
}
