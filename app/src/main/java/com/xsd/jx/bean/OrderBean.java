package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class OrderBean implements MultiItemEntity, Serializable {

    /**
     address	string 上工地点
     advanceType	integer 结算方式 预付款类型 1:两成 2:全款 3:不预付
     allEarned	integer 赚得收入
     confirmedAt	string 确认时间
     createdAt	string 报名时间
     cancelAt	string 取消时间
     day	integer 工期(天数)
     endDate	string 结束日期
     id	integer ID
     isSafe	integer 是否购买保险 0:否 1:是
     listId	integer 用工ID
     num	integer 所需人数
     price	integer 工价（元）
     settleAt	string 结算时间
     settleType	integer  结算方式 1:日结 2:做完再结
     sn	string 报名编号
     startDate	string 开始日期
     status	integer 状态 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
     typeId	integer 工种ID
     typeTitle	string 工种名称
     userId	integer  用户ID
     workDays	integer 已上工天数
     */


    private String address;
    private int advanceType;
    private int allEarned;
    private String confirmedAt;
    private String createdAt;
    private String cancelAt;
    private int day;
    private String endDate;
    private int id;
    private int isSafe;
    private int listId;
    private int num;
    private String price;
    private String settleAt;
    private int settleType;
    private String sn;
    private String startDate;
    private int status;//状态 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    private int typeId;
    private String typeTitle;
    private int userId;
    private int workDays;
    private String employerPhone;

    public String getCancelAt() {
        return cancelAt;
    }

    public void setCancelAt(String cancelAt) {
        this.cancelAt = cancelAt;
    }

    public String getEmployerPhone() {
        return employerPhone;
    }

    public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }

    @Override
    public int getItemType() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(int advanceType) {
        this.advanceType = advanceType;
    }

    public int getAllEarned() {
        return allEarned;
    }

    public void setAllEarned(int allEarned) {
        this.allEarned = allEarned;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(int isSafe) {
        this.isSafe = isSafe;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSettleAt() {
        return settleAt;
    }

    public void setSettleAt(String settleAt) {
        this.settleAt = settleAt;
    }

    public int getSettleType() {
        return settleType;
    }

    public void setSettleType(int settleType) {
        this.settleType = settleType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }


}
