package com.forest.algotrade.stockloader.bean;


/**
 * Created by dev on 15-8-1.
 */

public class DailyTransaction {


    public DailyTransaction(String stockCode, String date, String marketValue, String pb)
    {
        this.setDate(date);
        this.setMarketValue(marketValue);
        this.setStockCode(stockCode);
        this.setPb(pb);

    }

    @Override
    public String toString() {
        return "DailyTransaction{" +
                "stockCode='" + stockCode + '\'' +
                ", date='" + date + '\'' +
                ", marketValue=" + marketValue +
                ", pb=" + pb +
                '}';
    }

    private String stockCode;

    private String date;

    private String marketValue;

    private String pb;     //price-to-book

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getPb() {
        return pb;
    }

    public void setPb(String pb) {
        this.pb = pb;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyTransaction that = (DailyTransaction) o;

        if (!stockCode.equals(that.stockCode)) return false;
        if (!date.equals(that.date)) return false;
        if (!marketValue.equals(that.marketValue)) return false;
        return pb.equals(that.pb);

    }

    @Override
    public int hashCode() {
        int result = stockCode.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + marketValue.hashCode();
        result = 31 * result + pb.hashCode();
        return result;
    }
}
