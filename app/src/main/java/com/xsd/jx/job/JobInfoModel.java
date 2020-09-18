package com.xsd.jx.job;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.inject.DataProvider;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public class JobInfoModel {
    private int workId;
    private JobInfoCallback callback;
    private DataProvider dataProvider;
    public JobInfoModel(JobInfoCallback callback) {
        this.callback = callback;
        this.workId = callback.getWorkId();
        this.dataProvider = callback.getDataProvider();
    }

     void detail() {
       dataProvider.work.detail(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<JobBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<JobBean> baseResponse) {
                        callback.detailCallBack(baseResponse.getData());
                    }
                });
    }

     void fav(){
         dataProvider.work.fav(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        callback.favCallBack(baseResponse.getData());
                    }
                });

    }
     void join() {
         dataProvider.work.join(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        callback.joinCallBack(baseResponse.getData());
                    }
                });
    }
}
