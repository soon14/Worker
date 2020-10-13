package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/10/12
 * author: SmallCake
 */
public class PaidResponse implements Serializable {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
