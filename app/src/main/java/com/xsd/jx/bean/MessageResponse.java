package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class MessageResponse implements Serializable {

    /**
     * items : [{"content":"string","createdAt":"string","dataId":0,"id":0,"status":0,"title":"string"}]
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

    public static class ItemsBean implements Serializable{
        /**
         * content : string
         * createdAt : string
         * dataId : 0
         * id : 0
         * status : 0 状态 1未读 2已读
         * title : string
         */

        private String content;
        private String createdAt;
        private int dataId;
        private int id;
        private int status;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
