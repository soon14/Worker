package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class RecommendResponse implements Serializable {

    /**
     * page : 1
     * totalPage : 1
     * count : 3
     * earned : 200
     * items : [{"userId":1,"name":"张工","avatar":"","helpEarned":10,"status":1,"desc":"提醒TA完成首单，您得10元"},{"userId":2,"name":"王牛","avatar":"","helpEarned":50,"status":2,"desc":"工友休息中"},{"userId":3,"name":"耍哇","avatar":"","helpEarned":70,"status":3,"desc":"3天后完工，您预计获得30元"}]
     */

    private int page;
    private int totalPage;
    private int count;
    private int earned;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEarned() {
        return earned;
    }

    public void setEarned(int earned) {
        this.earned = earned;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * userId : 1
         * name : 张工
         * avatar :
         * helpEarned : 10
         * status : 1
         * desc : 提醒TA完成首单，您得10元
         */

        private int userId;
        private String name;
        private String avatar;
        private int helpEarned;
        private int status;
        private String desc;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getHelpEarned() {
            return helpEarned;
        }

        public void setHelpEarned(int helpEarned) {
            this.helpEarned = helpEarned;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
