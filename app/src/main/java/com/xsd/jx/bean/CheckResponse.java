package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/26
 * author: SmallCake
 * 用户考勤主界面信息
 */
public class CheckResponse implements Serializable {

    /**
     address	 string 上工地点
     avgHour	 number 平均工时
     day	     integer 工期(天数)
     mobile	     string 雇主手机号
     signInDesc	 string 签到说明
     signInPic	 string 签到图片
     signInTime	 string 签到时间
     signOutDesc string 签退说明
     signOutPic	 string 签退图片
     signOutTime string 签退时间
     workId	     integer 工作ID
     */

    private String address="";
    private String avgHour;
    private int day;
    private String mobile;
    private String signInDesc;
    private String signInPic;
    private String signInTime;
    private String signOutDesc;
    private String signOutPic;
    private String signOutTime;
    private int workId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvgHour() {
        return avgHour;
    }

    public void setAvgHour(String avgHour) {
        this.avgHour = avgHour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }
}
