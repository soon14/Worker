package com.xsd.jx.bean;

/**
 * Date: 2020/10/12
 * author: SmallCake
 */
public class PaidResponse extends MessageBean {
    private String orderString;

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
}
