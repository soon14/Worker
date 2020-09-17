package com.xsd.jx.impl;

import com.xsd.jx.api.UserApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.HelpRegResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MessageResponse;
import com.xsd.jx.bean.RecommendResponse;
import com.xsd.jx.bean.UserInfoResponse;

import java.util.List;
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
    public Observable<BaseResponse<List<JobBean>>> favWorks(Integer page) {
        return bindIoUI(api.favWorks(page));
    }

    @Override
    public Observable<BaseResponse<HelpRegResponse>> helpRegRecord(Integer page) {
        return bindIoUI(api.helpRegRecord(page));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> uploadAvatar(MultipartBody.Part body) {
        return bindIoUI(api.uploadAvatar(body));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> profile(Map<String, RequestBody> map) {
        return bindIoUI(api.profile(map));
    }

    @Override
    public Observable<BaseResponse<RecommendResponse>> recommend(Integer page) {
        return bindIoUI(api.recommend(page));
    }

    @Override
    public Observable<BaseResponse<MessageResponse>> message(Integer type, Integer page) {
        return bindIoUI(api.message(type,page));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> helpReg(String mobile, String code, String name, String idCard, String wtIds,String nation,String workYears) {
        return bindIoUI(api.helpReg(mobile,code,name,idCard,wtIds,nation,workYears));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> certification(String avatar, String name, String idCard, String nation, String workYears) {
        return bindIoUI(api.certification(avatar,name,idCard,nation,workYears));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> cityPartner(String name, String mobile, Integer idType, Integer purpose) {
        return bindIoUI(api.cityPartner(name,mobile,idType,purpose));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> feedback(String content, String contentUrl) {
        return bindIoUI(api.feedback(content,contentUrl));
    }


}
