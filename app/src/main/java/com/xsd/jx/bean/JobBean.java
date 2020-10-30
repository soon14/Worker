package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/8/18
 * author: SmallCake
 */
public class JobBean implements Serializable {


    /**
     * id : 2
     * userId : 2
     * typeId : 7
     * address : 湖北省武汉市汉阳区建龙大道社区挨着轻轨站
     * startDate : 2020-09-12
     * endDate : 2020-09-15
     * day : 3
     * price : 320
     * settleType : 1
     * advanceType : 1
     * num : 3
     * desc : 诚意招工，要求踏实肯干，有意者直接报名
     * isSafe : 1
     * status : 1
     * isFav : false
     * isJoin : false
     * publisher : 柱子
     * division : 武汉事业部
     * typeTitle : 杠精工
     */

    private int id;
    private int userId;
    private int typeId;
    private String address;
    private String startDate;
    private String endDate;
    private int day;
    private String price;
    private int settleType;
    private int advanceType;
    private int num;
    private String desc;
    private int isSafe;
    private int status;
    private boolean isFav;
    private boolean isJoin;
    private String publisher;
    private String division;
    private String divisionAddr;
    private String typeTitle;

    private int joinedNum;//已报名人数

    public int getJoinedNum() {
        return joinedNum;
    }

    public void setJoinedNum(int joinedNum) {
        this.joinedNum = joinedNum;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public String getDivisionAddr() {
        return divisionAddr;
    }

    public void setDivisionAddr(String divisionAddr) {
        this.divisionAddr = divisionAddr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSettleType() {
        return settleType;
    }

    public void setSettleType(int settleType) {
        this.settleType = settleType;
    }

    public int getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(int advanceType) {
        this.advanceType = advanceType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(int isSafe) {
        this.isSafe = isSafe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isIsFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }

    public boolean isIsJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }
}
