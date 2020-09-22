package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.bean.WorkerResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     //我的招工
    @GET("server/work-list")
    Observable<BaseResponse<MyGetWorkersResponse>> workList(@Query("page")Integer page, @Query("type")Integer type);

    /**
     * 发布招工
     * @param typeId        工种ID
     * @param address       上工地点
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @param price         工价（元）
     * @param desc          岗位说明
     * @param num           所需人数
     * @param isSafe        是否购买保险 0:否 1:是
     * @param settleType    结算方式 1:日结 2:做完再结
     * @param advanceType   结算方式 预付款类型 1:两成 2:全款 3:不预付
     * @param safeAmount    保险费用
     * @param advanceAmount 预付款金额
     */
    @FormUrlEncoded
    @POST("server/publish-work")
    Observable<BaseResponse<MessageBean>> publishWork(
            @Field("typeId") Integer typeId
            , @Field("address")String address
            , @Field("startDate")String startDate
            , @Field("endDate")String endDate
            , @Field("price")Integer price
            , @Field("desc")String desc
            , @Field("num")Integer num
            , @Field("isSafe")Integer isSafe
            , @Field("settleType")Integer settleType
            , @Field("advanceType")Integer advanceType
            , @Field("safeAmount")String safeAmount
            , @Field("advanceAmount")String advanceAmount
    );

}
