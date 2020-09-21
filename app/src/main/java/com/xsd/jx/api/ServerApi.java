package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.bean.WorkerResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Date: 2020/9/21
 * author: SmallCake
 */
public interface ServerApi {
    //获取一个工人简历详情
    @GET("server/cv")
    Observable<BaseResponse<WorkerInfoResponse>> cv(@Query("userId")Integer userId);
    //企业端首页推荐用户列表
    @GET("server/index")
    Observable<BaseResponse<WorkerResponse>> index(@Query("page")Integer page, @Query("wtId")Integer wtId, @Query("sortBy")Integer sortBy);

}
