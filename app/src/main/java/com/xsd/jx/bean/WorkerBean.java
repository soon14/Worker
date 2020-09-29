package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Date: 2020/9/21
 * author: SmallCake
 */
public class WorkerBean implements Serializable, MultiItemEntity {

    /**
     * userId : 1
     * name : 张三
     * birthday : 1975-05-18
     * sex : 1
     * avatar :
     * isCertification : 1
     * workYears : 10-15
     * age : 0
     */

    private int userId;
    private String name;
    private String birthday;
    private int sex;
    private String avatar;
    private int isCertification;
    private String workYears;
    private int age;

    //招工多余参数
    private String mobile;
    private int status;
    private int checkDay;
    private int totalWage;

     /** type 状态 -1:不展示(有预付款项未付不显示给用户) )
 *  1:正在招
 *  2:已招满/待开工(所有用户已确认)
 *  3:工期中
 *  4:待结算
 *  5:待评价
 *  6:已完成
 *  7:已取消
 *  8:已过期
 */
    private int type=1;//用于我的招工 - 招工详情的工人列表

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(int checkDay) {
        this.checkDay = checkDay;
    }

    public int getTotalWage() {
        return totalWage;
    }

    public void setTotalWage(int totalWage) {
        this.totalWage = totalWage;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
