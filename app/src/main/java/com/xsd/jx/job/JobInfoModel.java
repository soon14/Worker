package com.xsd.jx.job;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public class JobInfoModel {
    private static JobInfoView view;
    private int workId;

    public JobInfoModel(JobInfoView view) {
        this.view = view;
        this.workId = view.getWorkId();
    }

     void detail() {
        view.getBaseActivity().getDataProvider().work.detail(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<JobBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<JobBean> baseResponse) {
                        view.detailCallBack(baseResponse);
                    }
                });
    }

     void fav(){
        view.getBaseActivity().getDataProvider().work.fav(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        view.favCallBack(baseResponse);
                    }
                });

    }
     void join() {
        view.getBaseActivity().getDataProvider().work.join(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        view.joinCallBack(baseResponse);
                    }
                });
    }
}
