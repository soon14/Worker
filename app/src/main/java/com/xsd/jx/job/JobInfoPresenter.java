package com.xsd.jx.job;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public class JobInfoPresenter {
    private JobInfoView view;
    private JobInfoModel model;

    public JobInfoPresenter(JobInfoView view) {
        this.view = view;
        model = new JobInfoModel(view);
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
