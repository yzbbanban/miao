package com.uuabb.miao.entity;

import java.util.Map;

/**
 * Created by ban on 2018/4/19.
 */
public class PoloniexCoinDateInfo {


    private Map<String, PoloniexCoinDateDetailInfo> list;


    class PoloniexCoinDateDetailInfo {
        /**
         * id : 7
         * last : 0.00000055
         * lowestAsk : 0.00000056
         * highestBid : 0.00000055
         * percentChange : 0.03773584
         * baseVolume : 97.91591910
         * quoteVolume : 182548902.44246730
         * isFrozen : 0
         * high24hr : 0.00000058
         * low24hr : 0.00000051
         */

        private int id;
        private String last;
        private String lowestAsk;
        private String highestBid;
        private String percentChange;
        private String baseVolume;
        private String quoteVolume;
        private String isFrozen;
        private String high24hr;
        private String low24hr;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getLowestAsk() {
            return lowestAsk;
        }

        public void setLowestAsk(String lowestAsk) {
            this.lowestAsk = lowestAsk;
        }

        public String getHighestBid() {
            return highestBid;
        }

        public void setHighestBid(String highestBid) {
            this.highestBid = highestBid;
        }

        public String getPercentChange() {
            return percentChange;
        }

        public void setPercentChange(String percentChange) {
            this.percentChange = percentChange;
        }

        public String getBaseVolume() {
            return baseVolume;
        }

        public void setBaseVolume(String baseVolume) {
            this.baseVolume = baseVolume;
        }

        public String getQuoteVolume() {
            return quoteVolume;
        }

        public void setQuoteVolume(String quoteVolume) {
            this.quoteVolume = quoteVolume;
        }

        public String getIsFrozen() {
            return isFrozen;
        }

        public void setIsFrozen(String isFrozen) {
            this.isFrozen = isFrozen;
        }

        public String getHigh24hr() {
            return high24hr;
        }

        public void setHigh24hr(String high24hr) {
            this.high24hr = high24hr;
        }

        public String getLow24hr() {
            return low24hr;
        }

        public void setLow24hr(String low24hr) {
            this.low24hr = low24hr;
        }

        @Override
        public String toString() {
            return "PoloniexCoinDateDetailInfo{" +
                    "id=" + id +
                    ", last='" + last + '\'' +
                    ", lowestAsk='" + lowestAsk + '\'' +
                    ", highestBid='" + highestBid + '\'' +
                    ", percentChange='" + percentChange + '\'' +
                    ", baseVolume='" + baseVolume + '\'' +
                    ", quoteVolume='" + quoteVolume + '\'' +
                    ", isFrozen='" + isFrozen + '\'' +
                    ", high24hr='" + high24hr + '\'' +
                    ", low24hr='" + low24hr + '\'' +
                    '}';
        }
    }

    public Map<String, PoloniexCoinDateDetailInfo> getList() {
        return list;
    }

    public void setList(Map<String, PoloniexCoinDateDetailInfo> list) {
        this.list = list;
    }
}
