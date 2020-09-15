package com.xsd.jx.impl;

import com.xsd.jx.api.UserApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfoResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    @Override
    public Observable<BaseResponse<MessageBean>> uploadAvatar(MultipartBody.Part body) {
        return bindIoUI(api.uploadAvatar(body));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> profile(Map<String, RequestBody> map) {
        return bindIoUI(api.profile(map));
    }


}
