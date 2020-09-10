package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public interface SiteApi {
    @FormUrlEncoded
    @POST("site/login-by-sms")
    Observable<BaseResponse<LoginUserResponse>> loginBySms(@Field("mobile")String mobile, @Field("code")String code, @Field("inviteCode")String inviteCode);
    @FormUrlEncoded
    @POST("site/login-by-wx")
    Observable<BaseResponse> loginByWx(@Field("unionid")String unionid,@Field("access_token")String access_token,@Field("openid")String openid,@Field("inviteCode")String inviteCode);
    @FormUrlEncoded
    @POST("site/send-sms")
    Observable<BaseResponse<MessageBean>> sendSms(@Field("mobile")String mobile, @Field("type")String type);
}
