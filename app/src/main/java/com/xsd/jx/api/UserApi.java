package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.UserInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public interface UserApi {
    //获取用户资料
    @GET("user/info")
    Observable<BaseResponse<UserInfoResponse>> info();
    //用户工作经历
    @GET("user/experience")
    Observable<BaseResponse<ExperienceResponse>> experience(@Query("page")Integer page);
}
