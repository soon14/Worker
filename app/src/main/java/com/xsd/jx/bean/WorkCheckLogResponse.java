package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/23
 * author: SmallCake
 */
public class WorkCheckLogResponse implements Serializable {


    /**
     * items : [{"checkId":1,"userId":1,"userName":"钢铁哥","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","mobile":"189999999999","signInTime":"09:23","status":1},{"checkId":2,"userId":2,"userName":"馒头哥","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","mobile":"189999922222","signInTime":"09:05","status":0},{"checkId":0,"userId":2,"userName":"馒头哥","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","mobile":"189999922222","signInTime":"","status":0}]
     * totalNum : 3
     * notCheckNum : 1
     * checkNum : 2
     */

    private String totalNum;
    private String notCheckNum;
    private String checkNum;
    private List<WorkingItem> workingList;
    private List<ItemsBean> items;

    public List<WorkingItem> getWorkingList() {
        return workingList;
    }

    public void setWorkingList(List<WorkingItem> workingList) {
        this.workingList = workingList;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getNotCheckNum() {
        return notCheckNum;
    }

    public void setNotCheckNum(String notCheckNum) {
        this.notCheckNum = notCheckNum;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        /**
         * checkId : 1
         * userId : 1
         * userName : 钢铁哥
         * avatar : https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg
         * mobile : 189999999999
         * signInTime : 09:23
         * status : 1
         */

        private int checkId;
        private int userId;
        private String userName;
        private String avatar;
        private String mobile;
        private String signInTime;
        private String signOutTime;
        private int status;

        public int getCheckId() {
            return checkId;
        }

        public void setCheckId(int checkId) {
            this.checkId = checkId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSignInTime() {
            return signInTime;
        }

        public void setSignInTime(String signInTime) {
            this.signInTime = signInTime;
        }

        public String getSignOutTime() {
            return signOutTime;
        }

        public void setSignOutTime(String signOutTime) {
            this.signOutTime = signOutTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public static class WorkingItem {

        /**
         id	        integer工作ID
         endDate	string结束日期
         startDate	string开始日期
         typeTitle	string工种名称
         */

        private int id;
        private String typeTitle;
        private String startDate;
        private String endDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
