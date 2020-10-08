package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.OrderResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public interface OrderApi {

    //订单列表 type:类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    @GET("order/list")
    Observable<BaseResponse<OrderResponse>> list(@Query("page")Integer page, @Query("type")Integer type);
    //取消订单
    @GET("order/cancel")
    Observable<BaseResponse<MessageBean>> cancel(@Query("id")Integer id);
    //评价雇主

    /**
     *
     @param joinId           报名ID
     @param toUserId         雇主ID
     @param workId           工作ID
     @param content          评价内容
     @param rate1            结算效率评分
     @param rate2            确认效率评分
     @param rate3            态度评分
     @param allRate          总体评分
     @param isAnonymous      是否匿名 1:是 2: 否
     */
    @FormUrlEncoded
    @POST("order/comment")
    Observable<BaseResponse<MessageBean>> comment(
            @Field("joinId") Integer joinId,
            @Field("toUserId") Integer toUserId,
            @Field("workId") Integer workId,
            @Field("content") String content,
            @Field("rate1") Integer rate1,
            @Field("rate2") Integer rate2,
            @Field("rate3") Integer rate3,
            @Field("allRate") Integer allRate,
            @Field("isAnonymous") Integer isAnonymous
    );
}
