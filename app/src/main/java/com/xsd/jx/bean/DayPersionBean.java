package com.xsd.jx.bean;

import com.xsd.utils.RandomUtils;

import java.io.Serializable;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class DayPersionBean implements Serializable {
    /**
     * id : 1
     * workDate : 2020-11-04
     * workNum : 6
     * isSettle : 1
     */

    private Integer id;
    private String workDate;
    private Integer workNum;
    private Integer isSettle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public Integer getWorkNum() {
        return workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

    public Integer getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }
}
