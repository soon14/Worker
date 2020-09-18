package com.xsd.jx.job;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public class JobInfoPresenter {
    private JobInfoModel model;
    public JobInfoPresenter(JobInfoCallback callback) {
        model = new JobInfoModel(callback);
    }
    //详情
    public void detail(){
        model.detail();
    }
    //收藏
    public void fav(){
        model.fav();
    }
    //报名上工
    public void join(){
        model.join();
    }

}
