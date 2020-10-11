package com.xsd.jx.impl;

import com.xsd.jx.api.ServerApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.PriceBean;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.bean.UserMonthLogResponse;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.bean.WorkCheckResponse;
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

    @Override
    public Observable<BaseResponse<JobListResponse>> invite(Integer userId, Integer wtId, Integer workId) {
        return bindIoUI(api.invite( userId,  wtId,  workId));
    }

    @Override
    public Observable<BaseResponse<MyGetWorkersResponse>> workList(Integer page, Integer type) {
        return bindIoUI(api.workList(page,type));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> publishWork(Integer typeId, String address, String startDate, String endDate, Integer price, String desc, Integer num, Integer isSafe, Integer settleType, Integer advanceType, String safeAmount, String advanceAmount) {
        return bindIoUI(api.publishWork(typeId,address,startDate,endDate,price,desc,num,isSafe,settleType,advanceType,safeAmount,advanceAmount));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> cancelWork(Integer workId) {
        return bindIoUI(api.cancelWork(workId));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> doJoinWorker(Integer workId, Integer userId, Integer type) {
        return bindIoUI(api.doJoinWorker(workId,userId,type));
    }

    @Override
    public Observable<BaseResponse<WorkCheckResponse>> workCheck(String date) {
        return bindIoUI(api.workCheck(date));
    }

    @Override
    public Observable<BaseResponse<WorkCheckLogResponse>> workCheckLog(String date, Integer workId, Integer status) {
        return bindIoUI(api.workCheckLog(date,workId,status));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> confirmCheckLog(Integer checkId) {
        return bindIoUI(api.confirmCheckLog(checkId));
    }

    @Override
    public Observable<BaseResponse<UserMonthLogResponse>> userCheckLogByMonth(Integer workId, Integer userId, String month) {
        return bindIoUI(api.userCheckLogByMonth(workId,userId,month));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> workComment(Integer workId, String data) {
        return bindIoUI(api.workComment(workId,data));
    }

    @Override
    public Observable<BaseResponse<ToSettleResponse>> settle() {
        return bindIoUI(api.settle());
    }

    @Override
    public Observable<BaseResponse<MessageBean>> doSettle(String ids) {
        return bindIoUI(api.doSettle(ids));
    }

    @Override
    public Observable<BaseResponse<PriceBean>> recommendPrice(Integer areaId, Integer typeId) {
        return bindIoUI(api.recommendPrice(areaId,typeId));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> helpCheck(Integer workId, Integer userId, String date) {
        return bindIoUI(api.helpCheck(workId,userId,date));
    }


}
