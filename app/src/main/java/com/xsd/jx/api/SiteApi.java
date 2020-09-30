package com.xsd.jx.api;

import com.xsd.jx.bean.BannerBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    //短信15分钟内有效，每分钟只能发一条，用于登录、绑定手机等操作,type: login, bindMobile, help
    @FormUrlEncoded
    @POST("site/send-sms")
    Observable<BaseResponse<MessageBean>> sendSms(@Field("mobile")String mobile, @Field("type")String type);
    //获取广告banner tid:类型 1:开屏 2:首页 3:城市合伙人 4:帮工友注册
    @GET("site/banner")
    Observable<BaseResponse<BannerBean>> banner(@Query("tId")Integer tId);
}
