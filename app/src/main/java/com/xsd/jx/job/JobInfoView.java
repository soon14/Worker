package com.xsd.jx.job;

import com.xsd.jx.base.BaseMvpView;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public interface JobInfoView extends BaseMvpView {
    int getWorkId();
    void detailCallBack(BaseResponse<JobBean> baseResponse);
    void favCallBack(BaseResponse<MessageBean> baseResponse);
    void joinCallBack(BaseResponse<MessageBean> baseResponse);

}
