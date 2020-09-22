package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
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

    /**
     * 邀请用户上工
     * @param userId 邀请用户ID
     * @param wtId 工种ID
     * @param workId 可选 工作ID，针对发布了多个相同工种的需求
     */
    @GET("server/invite")
    Observable<BaseResponse<JobListResponse>> invite(@Query("userId")Integer userId, @Query("wtId")Integer wtId, @Query("workId")Integer workId);

}
