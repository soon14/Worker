package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class BalanceLogResponse implements Serializable {

    /**
     * items : [{"createdAt":"string","id":0,"status":0,"type":"string","value":0}]
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
         * createdAt : string
         * id : 0
         * status : 0
         * type : string
         * value : 0
         */

        private String createdAt;
        private int id;
        private int status;//状态 1:冻结 2：解冻，status值增加一个为3，表示提现未到账，显示为红色
        private String statusText;//状态文本描述
        private String type;
        private int value;

        private String userName;//奖励激励独有字段

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
