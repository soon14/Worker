package com.xsd.jx.bean;


import java.io.Serializable;

/**
 * Date: 2019/11/25
 * author: SmallCake
 * 先判断 success 的值 ，为true  读取data里面的内容，为false  的话，除了 status 为 401的，需要跳转登录界面，其余状态码 皆提示 用户 message的内容
 */
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
