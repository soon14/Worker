package com.xsd.jx.bean;

/**
 * Date: 2020/10/11
 * author: SmallCake
 * 工人每天的签到记录信息
 */
public class DayCheckBean {
    /**
     employer	    string雇主名称
     id	            integer考勤ID
     listId	        integer工作ID
     signInDesc	    tring签到说明
     signInPic	    string 签到图片
     signInTime	    string签到时间
     signOutDesc	string签退说明
     signOutPic	    string签退图片
     signOutTime	string签退时间
     status	        integer确认状态 0:未确认 1:已确认
     userId	        integer用户ID
     workDate	    string 上工日期
     */
    //工人端独有字段
    private String employer;
    private int id;
    private int userId;
    private int listId;


    private String workDate;
    private String signInDesc;
    private String signInPic;
    private String signInTime;
    private String signOutDesc;
    private String signOutPic;
    private String signOutTime;
    private int status;//确认状态 1:未确认 2:已确认

    //企业端独有字段
    private int checkId;//考勤ID

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

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
