package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/23
 * author: SmallCake
 */
public class WorkCheckResponse implements Serializable {

    /**
     * works : 0
     * num : 0
     * notCheckNum : 0
     */

    private String works;
    private String num;
    private String notCheckNum;

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNotCheckNum() {
        return notCheckNum;
    }

    public void setNotCheckNum(String notCheckNum) {
        this.notCheckNum = notCheckNum;
    }
}
