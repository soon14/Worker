package com.xsd.jx.bean;

import java.util.List;

/**
 * Date: 2020/10/11
 * author: SmallCake
 */
public class UserMonthLogResponse {


    /**
     * items : [{"checkId":3,"workDate":"2020-10-11","signInPic":"http://yuemeoss.oss-cn-shenzhen.aliyuncs.com/user-avatars/20201011102527_2160x3840.png","signInDesc":"开始上工","signOutPic":"","signOutDesc":"","status":2,"signInTime":"10:25","signOutTime":"00:00"}]
     * allDays : 1
     * days : 1
     * hours : 0
     */

    private String allDays;
    private String days;
    private String hours;
    private List<DayCheckBean> items;

    public String getAllDays() {
        return allDays;
    }

    public void setAllDays(String allDays) {
        this.allDays = allDays;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public List<DayCheckBean> getItems() {
        return items;
    }

    public void setItems(List<DayCheckBean> items) {
        this.items = items;
    }

}
