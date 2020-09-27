package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/27
 * author: SmallCake
 */
public class PriceBean implements Serializable {
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
