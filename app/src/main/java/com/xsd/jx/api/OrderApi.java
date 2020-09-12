package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public interface OrderApi {

    //订单列表 type:类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    @GET("order/list")
    Observable<BaseResponse<OrderResponse>> list(@Query("page")Integer page, @Query("type")Integer type);
}
