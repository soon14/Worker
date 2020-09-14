package com.xsd.jx.bean;

import java.util.List;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class ExperienceResponse {

    /**
     * items : [{"endDate":"string","publisher":"string","rate":0,"startDate":"string","typeTitle":"string"}]
     * page : 0
     * totalPage : 0
     */

    private int page;
    private int totalPage;
    private List<ItemsBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * endDate : string
         * publisher : string
         * rate : 0
         * startDate : string
         * typeTitle : string
         */

        private String endDate;
        private String publisher;
        private int rate;
        private String startDate;
        private String typeTitle;

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
        }
    }
}
