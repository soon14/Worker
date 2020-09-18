package com.xsd.jx.job;

import com.xsd.jx.base.BaseMvpCallback;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public interface JobInfoCallback extends BaseMvpCallback {
    int getWorkId();
    void detailCallBack(JobBean jobBean);
    void favCallBack(MessageBean messageBean);
    void joinCallBack(MessageBean messageBean);

}
