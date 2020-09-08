package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class BaseStrResponse implements Serializable {
    private boolean success;
    public String data;
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
