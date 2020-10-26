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

    private String days="";
    private String earned="";
    private String hours="";
    private List<DayCheckBean> items;

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

    public List<DayCheckBean> getItems() {
        return items;
    }

    public void setItems(List<DayCheckBean> items) {
        this.items = items;
    }

}
