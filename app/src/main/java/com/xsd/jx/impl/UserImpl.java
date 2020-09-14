package com.xsd.jx.impl;

import com.xsd.jx.api.UserApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.UserInfoResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class UserImpl implements UserApi {
    @Inject
    UserApi api;
    @Inject
    public UserImpl() {
    }

    @Override
    public Observable<BaseResponse<UserInfoResponse>> info() {
        return bindIoUI(api.info());
    }

    @Override
    public Observable<BaseResponse<ExperienceResponse>> experience(Integer page) {
        return bindIoUI(api.experience(page));
    }
}
