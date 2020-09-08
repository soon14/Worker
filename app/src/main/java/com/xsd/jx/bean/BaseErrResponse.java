package com.xsd.jx.bean;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public  class BaseErrResponse  extends BaseObservable implements Serializable {
    private static final String serialVersionUID = "4369dbd9f6d1d000";
    public BaseErrResponse() {
    }

    private String name;
    private String message;
    private int code;
    private int status;

    private BaseErrResponse(Builder builder) {
        setName(builder.name);
        setMessage(builder.message);
        setCode(builder.code);
        setStatus(builder.status);
    }

    @Override
    public String toString() {
        return "BaseErrResponse{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", status=" + status +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final class Builder {
        private String name;
        private String message;
        private int code;
        private int status;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder code(int val) {
            code = val;
            return this;
        }

        public Builder status(int val) {
            status = val;
            return this;
        }

        public BaseErrResponse build() {
            return new BaseErrResponse(this);
        }
    }
}
