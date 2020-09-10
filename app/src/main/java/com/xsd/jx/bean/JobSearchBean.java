package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/8/18
 * author: SmallCake
 */
public class JobSearchBean implements Serializable {

    /**
     * id : 3
     * workId : 3
     * price : 350
     * typeTitle : 木工
     * unit : 天
     */

    private int id;
    private int workId;
    private String price;
    private String typeTitle;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
