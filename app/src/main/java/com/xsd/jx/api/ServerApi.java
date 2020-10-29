package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.PaidResponse;
import com.xsd.jx.bean.PriceBean;
import com.xsd.jx.bean.PushGetWorkersResponse;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.bean.UserMonthLogResponse;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.bean.WorkCheckResponse;
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
    /**
     * 我的招工
     * @param page 页码
     * @param type 类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成 7:已取消
     * @return
     */
    @GET("server/work-list")
    Observable<BaseResponse<MyGetWorkersResponse>> workList(@Query("page")Integer page, @Query("type")Integer type);
    /**
     * 我的招工详情
     * 根据ID获取我发布的招工详细数据
     * @param workId 工作ID
     * {@link com.xsd.jx.manager.GetWorkersInfoActivity}
     */
    @GET("server/work-detail")
    Observable<BaseResponse<MyGetWorkersResponse.ItemsBean>> workDetail(@Query("workId")Integer workId);

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
     * @param payment       付款方式 1.微信2.支付宝
     *
     * @param provinceId    省ID
     * @param cityId        市ID
     * @param areaId        区ID
     */
    @FormUrlEncoded
    @POST("server/publish-work")
    Observable<BaseResponse<PushGetWorkersResponse>> publishWork(
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
            , @Field("advanceAmount")Integer advanceAmount
            , @Field("payment")Integer payment
            , @Field("provinceId")Integer provinceId
            , @Field("cityId")Integer cityId
            , @Field("areaId")Integer areaId
    );

    //取消招聘:在还没有人报名的情况下，发布者可以取消招聘
    @GET("server/cancel-work")
    Observable<BaseResponse<MessageBean>> cancelWork(@Query("workId")Integer workId);

    /**
     * 拒绝/雇佣报名用户
     * @param workId 用工ID
     * @param userId 报名用户ID
     * @param type 类型 1:拒绝 2:雇佣
     * @return
     */
    @FormUrlEncoded
    @POST("server/do-join-worker")
    Observable<BaseResponse<MessageBean>> doJoinWorker(@Field("workId")Integer workId,@Field("userId")Integer userId,@Field("type")Integer type);


    /**
     * 工人考勤概况
     * @param date 日期 格式 2006-01-02，可以不传，默认今日
     */
    @GET("server/work-check")
    Observable<BaseResponse<WorkCheckResponse>> workCheck(@Query("date")String date);

    /**
     * 考勤记录
     * 获取日期获取某个招工的所有用户的考勤信息
     * @param date 日期 格式 2006-01-02，可以不传，默认今日
     * @param workId 工作ID
     * @param status 状态 0:全部 1：未考勤 2:已考勤
     */
    @GET("server/work-check-log")
    Observable<BaseResponse<WorkCheckLogResponse>> workCheckLog(@Query("date")String date, @Query("workId")Integer workId, @Query("status")Integer status);

    /**
     * 确认考勤
     * 获取日期获取某个招工的所有用户的考勤信息
     * @param checkId 考勤ID
     */
    @GET("server/confirm-check-log")
    Observable<BaseResponse<MessageBean>> confirmCheckLog(@Query("checkId")Integer checkId );

    /**
     * 获取用户整月的考勤记录
     * 根据上工记录获取用户整月的上工记录
     * @param workId 工作ID
     * @param userId 用户ID
     * @param month 月份 格式 2020-01，可以不传，默认当月
     */
    @GET("server/user-check-log-by-month")
    Observable<BaseResponse<UserMonthLogResponse>> userCheckLogByMonth(@Query("workId")Integer workId, @Query("userId")Integer userId, @Query("month")String month );

    /**
     * 评价
     * 用工方评价上工者
     * @param workId
     * @param data  {
     *   "allRate": 0,
     *   "content": "string",
     *   "id": 0,
     *   "isAnonymous": 0,
     *   "rate1": 0,
     *   "rate2": 0,
     *   "rate3": 0,
     *   "toUserId": 0,
     *   "userId": 0,
     *   "workId": 0
     * }
     * @return
     */
    @FormUrlEncoded
    @POST("server/work-comment")
    Observable<BaseResponse<MessageBean>> workComment(@Field("workId")Integer workId,@Field("data")String data);

    //工资结算:获取当前用户共待结算的招工信息列表
    @GET("server/settle")
    Observable<BaseResponse<ToSettleResponse>> settle();

    /**
     * 工资结算提交
     * 提交支付用户需要结算的招工，成功后跳转支付
     * @param payment 支付方式:1:微信 2:支付宝
     * @param ids 选择提交的结算工作ID，用英文逗号分隔 1,2,3,4
     */
    @GET("server/do-settle")
    Observable<BaseResponse<PaidResponse>> doSettle(@Query("payment")Integer payment, @Query("ids")String ids);

    /**
     * 查工价
     * 根据地区获取最低工价，发布的工价不能低于此价格
     * @param areaId 地区ID
     * @param typeId 工种ID
     */
    @GET("server/recommend-price")
    Observable<BaseResponse<PriceBean>> recommendPrice(@Query("areaId")Integer areaId, @Query("typeId")Integer typeId);

    //补考勤：发布者帮助用户完善考勤
    @GET("server/help-check")
    Observable<BaseResponse<MessageBean>> helpCheck(@Query("workId")Integer workId,@Query("userId")Integer userId,@Query("date")String date);


}
