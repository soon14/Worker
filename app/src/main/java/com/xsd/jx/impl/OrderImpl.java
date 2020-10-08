package com.xsd.jx.impl;

import com.xsd.jx.api.OrderApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.OrderResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class OrderImpl implements OrderApi {
    @Inject
    OrderApi api;
    @Inject
    public OrderImpl() {}
    @Override
    public Observable<BaseResponse<OrderResponse>> list(Integer page, Integer type) {
        return bindIoUI(api.list(page,type));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> cancel(Integer id) {
        return bindIoUI(api.cancel(id));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> comment(Integer joinId, Integer toUserId, Integer workId, String content, Integer rate1, Integer rate2, Integer rate3, Integer allRate, Integer isAnonymous) {
        return bindIoUI(api.comment(joinId,toUserId,workId,content,rate1,rate2,rate3,allRate,isAnonymous));
    }
}
