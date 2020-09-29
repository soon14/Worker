package com.xsd.jx.bean;

import java.util.List;

/**
 * Date: 2020/9/21
 * author: SmallCake
 * 工人简历
 */
public class WorkerInfoResponse {

    /**
     * id : 5
     * name : 小二不拉客
     * birthday : 2020年9月16日
     * sex : 1
     * avatar :
     * nation : 佤族
     * intro : 我的经验不是一个十年可以说得完的！
     * isCertification : 0
     * workYears : 5-10年
     * age : 0
     * workTypes : []
     * experience : []
     */

    private int id;
    private String name;
    private String birthday;
    private int sex;
    private String avatar;
    private String nation;
    private String intro;
    private int isCertification;
    private String workYears;
    private int age;
    private List<WorkTypeBean> workTypes;
    private List<ExperienceResponse.ItemsBean> experience;

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

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public List<WorkTypeBean> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkTypeBean> workTypes) {
        this.workTypes = workTypes;
    }

    public List<ExperienceResponse.ItemsBean> getExperience() {
        return experience;
    }

    public void setExperience(List<ExperienceResponse.ItemsBean> experience) {
        this.experience = experience;
    }
}
