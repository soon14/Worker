package com.xsd.jx.impl;


import com.xsd.jx.api.AdvertApi;
import com.xsd.jx.bean.AdBean;
import com.xsd.jx.bean.BaseResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2019/11/25
 * author: SmallCake
 */
public class AdvertImpl implements AdvertApi {
    @Inject
    AdvertApi advertApi;
    @Inject
    public AdvertImpl() {}

    @Override
    public Observable<BaseResponse<AdBean>> startAd() {
        return bindIoUI(advertApi.startAd());
    }

}
