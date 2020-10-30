package com.xsd.jx.bean;

import com.xsd.utils.RandomUtils;

import java.io.Serializable;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class DayPersionBean implements Serializable {
    int num;

    public DayPersionBean(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
