package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/23
 * author: SmallCake
 */
public class WorkCheckResponse implements Serializable {


    /**
     * notCheckNum : 0
     * num : 0
     * works : 0
     */

    private String notCheckNum;
    private String num;
    private String works;

    public String getNotCheckNum() {
        return notCheckNum;
    }

    public void setNotCheckNum(String notCheckNum) {
        this.notCheckNum = notCheckNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }
}
