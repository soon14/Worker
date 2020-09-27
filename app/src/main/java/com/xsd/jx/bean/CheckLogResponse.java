package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/27
 * author: SmallCake
 */
public class CheckLogResponse implements Serializable {

    /**
     * days : 0
     * earned : 0
     * hours : 0
     * items : [{"employer":"string","id":0,"listId":0,"signInDesc":"string","signInPic":"string","signInTime":"string","signOutDesc":"string","signOutPic":"string","signOutTime":"string","status":0,"userId":0,"workDate":"string"}]
     */

    private String days;
    private String earned;
    private String hours;
    private List<ItemsBean> items;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getEarned() {
        return earned;
    }

    public void setEarned(String earned) {
        this.earned = earned;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * employer : string
         * id : 0
         * listId : 0
         * signInDesc : string
         * signInPic : string
         * signInTime : string
         * signOutDesc : string
         * signOutPic : string
         * signOutTime : string
         * status : 0
         * userId : 0
         * workDate : string
         */

        private String employer;
        private int id;
        private int listId;
        private String signInDesc;
        private String signInPic;
        private String signInTime;
        private String signOutDesc;
        private String signOutPic;
        private String signOutTime;
        private int status;
        private int userId;
        private String workDate;

        public String getEmployer() {
            return employer;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getListId() {
            return listId;
        }

        public void setListId(int listId) {
            this.listId = listId;
        }

        public String getSignInDesc() {
            return signInDesc;
        }

        public void setSignInDesc(String signInDesc) {
            this.signInDesc = signInDesc;
        }

        public String getSignInPic() {
            return signInPic;
        }

        public void setSignInPic(String signInPic) {
            this.signInPic = signInPic;
        }

        public String getSignInTime() {
            return signInTime;
        }

        public void setSignInTime(String signInTime) {
            this.signInTime = signInTime;
        }

        public String getSignOutDesc() {
            return signOutDesc;
        }

        public void setSignOutDesc(String signOutDesc) {
            this.signOutDesc = signOutDesc;
        }

        public String getSignOutPic() {
            return signOutPic;
        }

        public void setSignOutPic(String signOutPic) {
            this.signOutPic = signOutPic;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getWorkDate() {
            return workDate;
        }

        public void setWorkDate(String workDate) {
            this.workDate = workDate;
        }
    }
}
