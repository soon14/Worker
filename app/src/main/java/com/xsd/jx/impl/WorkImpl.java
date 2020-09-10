package com.xsd.jx.impl;

import com.xsd.jx.api.WorkApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.bean.WorkRecommendResponse;
import com.xsd.jx.bean.WorkTypeResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.xsd.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class WorkImpl implements WorkApi {
    @Inject
    WorkApi api;
    @Inject
    public WorkImpl() {
    }

    @Override
    public Observable<BaseResponse<List<WorkTypeResponse>>> workTypeList() {
        return bindIoUI(api.workTypeList());
    }

    @Override
    public Observable<BaseResponse<MessageBean>> workTypeSubmitChoice(String ids) {
        return bindIoUI(api.workTypeSubmitChoice(ids));
    }

    @Override
    public Observable<BaseResponse<WorkRecommendResponse>> recommend(Integer page) {
        return bindIoUI(api.recommend(page));
    }

    @Override
    public Observable<BaseResponse<List<JobBean>>> inviteList() {
        return bindIoUI(api.inviteList());
    }

    @Override
    public Observable<BaseResponse<WorkListResponse>> list(Integer page,Integer type) {
        return bindIoUI(api.list(page,type));
    }

    @Override
    public Observable<BaseResponse<List<JobSearchBean>>> price(Integer areaId, String keywords) {
        return bindIoUI(api.price(areaId,keywords));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> acceptInvite(Integer workId) {
        return bindIoUI(api.acceptInvite(workId));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> join(Integer workId) {
        return bindIoUI(api.join(workId));
    }
}
