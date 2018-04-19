package com.uuabb.miao.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */
public class BDate {


    /**
     * success : true
     * message : 
     * result : [{"MarketName":"BTC-2GIVE","High":1.33E-6,"Low":1.06E-6,"Volume":1.711369678053904E7,"Last":1.28E-6,"BaseVolume":21.15807645,"TimeStamp":"2018-04-19T00:58:43.027","Bid":1.27E-6,"Ask":1.28E-6,"OpenBuyOrders":296,"OpenSellOrders":825,"PrevDay":1.07E-6,"Created":"2016-05-16T06:44:15.287"},{"MarketName":"BTC-ABY","High":1.14E-6,"Low":1.05E-6,"Volume":7122511.52840394,"Last":1.13E-6,"BaseVolume":7.72730038,"TimeStamp":"2018-04-19T00:58:48.883","Bid":1.13E-6,"Ask":1.14E-6,"OpenBuyOrders":378,"OpenSellOrders":2043,"PrevDay":1.09E-6,"Created":"2014-10-31T01:43:25.743"}]
     */

    private boolean success;
    private String message;
    private List<ResultBean> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * MarketName : BTC-2GIVE
         * High : 1.33E-6
         * Low : 1.06E-6
         * Volume : 1.711369678053904E7
         * Last : 1.28E-6
         * BaseVolume : 21.15807645
         * TimeStamp : 2018-04-19T00:58:43.027
         * Bid : 1.27E-6
         * Ask : 1.28E-6
         * OpenBuyOrders : 296
         * OpenSellOrders : 825
         * PrevDay : 1.07E-6
         * Created : 2016-05-16T06:44:15.287
         */

        private String MarketName;
        private BigDecimal High;
        private BigDecimal Low;
        private BigDecimal Volume;
        private BigDecimal Last;
        private BigDecimal BaseVolume;
        private String TimeStamp;
        private BigDecimal Bid;
        private BigDecimal Ask;
        private int OpenBuyOrders;
        private int OpenSellOrders;
        private BigDecimal PrevDay;
        private String Created;

        public String getMarketName() {
            return MarketName;
        }

        public void setMarketName(String MarketName) {
            this.MarketName = MarketName;
        }

        public BigDecimal getHigh() {
            return High;
        }

        public void setHigh(BigDecimal High) {
            this.High = High;
        }

        public BigDecimal getLow() {
            return Low;
        }

        public void setLow(BigDecimal Low) {
            this.Low = Low;
        }

        public BigDecimal getVolume() {
            return Volume;
        }

        public void setVolume(BigDecimal Volume) {
            this.Volume = Volume;
        }

        public BigDecimal getLast() {
            return Last;
        }

        public void setLast(BigDecimal Last) {
            this.Last = Last;
        }

        public BigDecimal getBaseVolume() {
            return BaseVolume;
        }

        public void setBaseVolume(BigDecimal BaseVolume) {
            this.BaseVolume = BaseVolume;
        }

        public String getTimeStamp() {
            return TimeStamp;
        }

        public void setTimeStamp(String TimeStamp) {
            this.TimeStamp = TimeStamp;
        }

        public BigDecimal getBid() {
            return Bid;
        }

        public void setBid(BigDecimal Bid) {
            this.Bid = Bid;
        }

        public BigDecimal getAsk() {
            return Ask;
        }

        public void setAsk(BigDecimal Ask) {
            this.Ask = Ask;
        }

        public int getOpenBuyOrders() {
            return OpenBuyOrders;
        }

        public void setOpenBuyOrders(int OpenBuyOrders) {
            this.OpenBuyOrders = OpenBuyOrders;
        }

        public int getOpenSellOrders() {
            return OpenSellOrders;
        }

        public void setOpenSellOrders(int OpenSellOrders) {
            this.OpenSellOrders = OpenSellOrders;
        }

        public BigDecimal getPrevDay() {
            return PrevDay;
        }

        public void setPrevDay(BigDecimal PrevDay) {
            this.PrevDay = PrevDay;
        }

        public String getCreated() {
            return Created;
        }

        public void setCreated(String Created) {
            this.Created = Created;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "MarketName='" + MarketName + '\'' +
                    ", High=" + High +
                    ", Low=" + Low +
                    ", Volume=" + Volume +
                    ", Last=" + Last +
                    ", BaseVolume=" + BaseVolume +
                    ", TimeStamp='" + TimeStamp + '\'' +
                    ", Bid=" + Bid +
                    ", Ask=" + Ask +
                    ", OpenBuyOrders=" + OpenBuyOrders +
                    ", OpenSellOrders=" + OpenSellOrders +
                    ", PrevDay=" + PrevDay +
                    ", Created='" + Created + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BDate{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
