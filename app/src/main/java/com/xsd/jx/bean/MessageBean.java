package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class MessageBean implements Serializable {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
