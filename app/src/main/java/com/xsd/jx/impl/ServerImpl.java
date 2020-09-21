package com.xsd.jx.impl;

import com.xsd.jx.api.ServerApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.bean.WorkerResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2020/9/21
 * author: SmallCake
 */
public class ServerImpl implements ServerApi {
    @Inject
    ServerApi api;
    @Inject
    public ServerImpl() {
    }

    @Override
    public Observable<BaseResponse<WorkerInfoResponse>> cv(Integer userId) {
        return bindIoUI(api.cv(userId));
    }

    @Override
    public Observable<BaseResponse<WorkerResponse>> index(Integer page, Integer wtId, Integer sortBy) {
        return bindIoUI(api.index( page,  wtId,  sortBy));
    }
}
