package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class LoginUserResponse implements Serializable {

    /**
     * code : 0
     * message : OK
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1OTk1NTI4NTQsImlkIjo1LCJuYmYiOjE1OTk1NTI4NTR9.026uX9F5JlwGD8oaEZPk_oSGfroYIQyFCUS3DddRcgY","user":{"id":5,"name":"3nBCaHoK","mobile":"18324138218","birthday":"","sex":1,"avatar":"","nationId":0,"workAreaId":0,"status":1,"wxUnionId":"","intro":"","idCard":"","isCertification":0,"workYears":0,"balance":500,"liveBalance":0,"frozenBalance":500,"withdrawTotal":0,"isHelpRegister":0,"createdAt":"2020-09-08 16:14:14","updatedAt":"2020-09-08 16:14:14","inviteCode":11116},"is_first":true}
     */

    private boolean is_first;
    private String token;
    private UserInfo user;

    public boolean isIs_first() {
        return is_first;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
