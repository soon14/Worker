package com.xsd.jx.impl;

import com.xsd.jx.api.SiteApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.MessageBean;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class SiteImpl implements SiteApi {
    @Inject
    SiteApi api;

    @Inject
    public SiteImpl() {
    }

    @Override
    public Observable<BaseResponse<LoginUserResponse>> loginBySms(String mobile, String code, String inviteCode) {
        return bindIoUI(api.loginBySms(mobile,code,inviteCode));
    }

    @Override
    public Observable<BaseResponse> loginByWx(String unionid, String access_token, String openid, String inviteCode) {
        return bindIoUI(api.loginByWx(unionid,access_token,openid,inviteCode));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> sendSms(String mobile, String type) {
        return bindIoUI(api.sendSms(mobile,type));
    }
}
