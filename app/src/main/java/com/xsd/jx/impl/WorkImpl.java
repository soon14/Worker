package com.xsd.jx.impl;

import com.xsd.jx.api.WorkApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CheckLogResponse;
import com.xsd.jx.bean.CheckResponse;
import com.xsd.jx.bean.InviteListResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.bean.WorkRecommendResponse;
import com.xsd.jx.bean.WorkTypeBean;

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
    public Observable<BaseResponse<List<WorkTypeBean>>> workTypeList() {
        return bindIoUI(api.workTypeList());
    }

    @Override
    public Observable<BaseResponse<MessageBean>> workTypeSubmitChoice(String ids) {
        return bindIoUI(api.workTypeSubmitChoice(ids));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> workTypeRem(Integer id) {
        return bindIoUI(api.workTypeRem(id));
    }

    @Override
    public Observable<BaseResponse<WorkRecommendResponse>> recommend(Integer page) {
        return bindIoUI(api.recommend(page));
    }

    @Override
    public Observable<BaseResponse<InviteListResponse>> inviteList() {
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
    public Observable<BaseResponse<MessageBean>> join(Integer workId,Integer num) {
        return bindIoUI(api.join(workId,num));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> fav(Integer workId) {
        return bindIoUI(api.fav(workId));
    }

    @Override
    public Observable<BaseResponse<JobBean>> detail(Integer workId) {
        return bindIoUI(api.detail(workId));
    }

    @Override
    public Observable<BaseResponse<CheckResponse>> check() {
        return bindIoUI(api.check());
    }

    @Override
    public Observable<BaseResponse<MessageBean>> doCheck(Integer workId, String pic, String desc) {
        return bindIoUI(api.doCheck(workId,pic,desc));
    }

    @Override
    public Observable<BaseResponse<CheckLogResponse>> checkLog(String month) {
        return bindIoUI(api.checkLog(month));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> publish(Integer wtId, Integer provinceId, Integer cityId, Integer districtId, String startDate, String endDate, Integer num) {
        return bindIoUI(api.publish(wtId,provinceId,cityId,districtId,startDate,endDate,num));
    }
}
