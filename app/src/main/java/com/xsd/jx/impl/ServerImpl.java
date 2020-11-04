package com.xsd.jx.impl;

import com.xsd.jx.api.ServerApi;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.PaidResponse;
import com.xsd.jx.bean.PriceBean;
import com.xsd.jx.bean.PushGetWorkersResponse;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.bean.UnmatchedResponse;
import com.xsd.jx.bean.UserMonthLogResponse;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.bean.WorkCheckResponse;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.bean.WorkerResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.http.Query;

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
    public Observable<BaseResponse<JobListResponse>> invite(Integer userId, Integer wtId, Integer workId,Integer num,Integer fid) {
        return bindIoUI(api.invite(userId,wtId,workId,num,fid));
    }

    @Override
    public Observable<BaseResponse<MyGetWorkersResponse>> workList(Integer page, Integer type) {
        return bindIoUI(api.workList(page,type));
    }

    @Override
    public Observable<BaseResponse<MyGetWorkersResponse.ItemsBean>> workDetail(Integer workId) {
        return bindIoUI(api.workDetail(workId));
    }

    @Override
    public Observable<BaseResponse<PushGetWorkersResponse>> publishWork(Integer typeId, String address, String startDate, String endDate, Integer price, String desc, Integer num, Integer isSafe, Integer settleType, Integer advanceType, String safeAmount, Integer advanceAmount, Integer payment, Integer provinceId, Integer cityId, Integer areaId) {
        return bindIoUI(api.publishWork(typeId,address,startDate,endDate,price,desc,num,isSafe,settleType,advanceType,safeAmount,advanceAmount,payment,provinceId,cityId,areaId));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> cancelWork(Integer workId) {
        return bindIoUI(api.cancelWork(workId));
    }

    @Override
    public Observable<BaseResponse<UnmatchedResponse>> doJoinWorker(Integer joinId, Integer type, boolean isConfirmed) {
        return bindIoUI(api.doJoinWorker(joinId,type,isConfirmed));
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
    public Observable<BaseResponse<PaidResponse>> doSettle(Integer payment, String ids) {
        return bindIoUI(api.doSettle(payment,ids));
    }

    @Override
    public Observable<BaseResponse<PriceBean>> recommendPrice(Integer areaId, Integer typeId) {
        return bindIoUI(api.recommendPrice(areaId,typeId));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> helpCheck(Integer workId, Integer userId, String date) {
        return bindIoUI(api.helpCheck(workId,userId,date));
    }

    @Override
    public Observable<BaseResponse<MessageBean>> confirmWork(Integer workId) {
        return bindIoUI(api.confirmWork(workId));
    }


}
