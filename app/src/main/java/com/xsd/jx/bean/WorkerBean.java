package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/21
 * author: SmallCake
 */
public class WorkerBean implements Serializable {

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
}
