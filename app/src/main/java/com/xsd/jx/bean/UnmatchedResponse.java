package com.xsd.jx.bean;

/**
 * Date: 2020/11/3
 * author: SmallCake
 * 雇佣人数和对方申请人数不匹配
 */
public class UnmatchedResponse extends MessageBean{


    /**
     * num : 7
     * phone :
     * surplusNum : 6
     */

    private Integer num;
    private String phone;
    private Integer surplusNum;

    @Override
    public String toString() {
        return "UnmatchedResponse{" +
                "num=" + num +
                ", phone='" + phone + '\'' +
                ", surplusNum=" + surplusNum +
                '}';
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }
}