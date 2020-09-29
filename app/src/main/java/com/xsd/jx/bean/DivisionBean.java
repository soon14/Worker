package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/29
 * author: SmallCake
 */
public class DivisionBean implements Serializable {

    /**
     * id : 1
     * name : 武汉事业部
     * addr : 武汉市长江1路111号
     */

    private int id;
    private String name;
    private String addr;

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
