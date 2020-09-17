package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class UserInfo implements Serializable {

    /**
     * id : 5
     * name : 3nBCaHoK
     * mobile : 18324138218
     * birthday :
     * sex : 1
     * avatar :
     * nation : 汉族
     * workAreaId : 0
     * status : 1
     * wxUnionId :
     * intro :
     * idCard :
     * isCertification : 0
     * workYears : 0
     * balance : 500
     * liveBalance : 0
     * frozenBalance : 500
     * withdrawTotal : 0
     * isHelpRegister : 0
     * createdAt : 2020-09-08 16:14:14
     * updatedAt : 2020-09-08 16:14:14
     * inviteCode : 11116
     */

    private int id;
    private String name;
    private String mobile;
    private String birthday;
    private int sex;
    private String avatar;
    private String nation;
    private int workAreaId;
    private int status;
    private String wxUnionId;
    private String intro;
    private String idCard;
    private int isCertification;//是否实名认证 0否 1是
    private String workYears;
    private String balance;
    private int liveBalance;
    private int frozenBalance;
    private int withdrawTotal;
    private int isHelpRegister;
    private String createdAt;
    private String updatedAt;
    private int inviteCode;
    private boolean isChooseWork;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", nation=" + nation +
                ", workAreaId=" + workAreaId +
                ", status=" + status +
                ", wxUnionId='" + wxUnionId + '\'' +
                ", intro='" + intro + '\'' +
                ", idCard='" + idCard + '\'' +
                ", isCertification=" + isCertification +
                ", workYears=" + workYears +
                ", balance=" + balance +
                ", liveBalance=" + liveBalance +
                ", frozenBalance=" + frozenBalance +
                ", withdrawTotal=" + withdrawTotal +
                ", isHelpRegister=" + isHelpRegister +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", inviteCode=" + inviteCode +
                ", isChooseWork=" + isChooseWork +
                '}';
    }

    public boolean isChooseWork() {
        return isChooseWork;
    }

    public void setChooseWork(boolean chooseWork) {
        isChooseWork = chooseWork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNation() {
        return nation;
    }

    public void setNationId(String nation) {
        this.nation = nation;
    }

    public int getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(int workAreaId) {
        this.workAreaId = workAreaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(int isCertification) {
        this.isCertification = isCertification;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getLiveBalance() {
        return liveBalance;
    }

    public void setLiveBalance(int liveBalance) {
        this.liveBalance = liveBalance;
    }

    public int getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(int frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public int getWithdrawTotal() {
        return withdrawTotal;
    }

    public void setWithdrawTotal(int withdrawTotal) {
        this.withdrawTotal = withdrawTotal;
    }

    public int getIsHelpRegister() {
        return isHelpRegister;
    }

    public void setIsHelpRegister(int isHelpRegister) {
        this.isHelpRegister = isHelpRegister;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }
}
