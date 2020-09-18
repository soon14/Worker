package com.xsd.jx.job;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityJobInfoBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.PopShowUtils;

/**
 * 详细招工信息
 */
public class JobInfoActivity extends BaseBindBarActivity<ActivityJobInfoBinding> implements JobInfoCallback {
    private int workId;
    private JobInfoPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }
    private void initView() {
        workId = getIntent().getIntExtra("type", 0);
        presenter = new JobInfoPresenter(this);
        presenter.detail();
    }

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.tv_fav:
                    presenter.fav();
                    break;
                case R.id.tv_share:
                    PopShowUtils.showShare(JobInfoActivity.this);
                    break;
                case R.id.tv_join:
                    presenter.join();
                    break;
            }
        });
    }


    @Override
    public int getWorkId() {
        return workId;
    }

    @Override
    public void detailCallBack(JobBean data) {
        db.setItem(data);
    }

    @Override
    public void favCallBack(MessageBean baseResponse) {
        DataBindingAdapter.isFav(db.tvFav,true);
    }

    @Override
    public void joinCallBack(MessageBean baseResponse) {
        PopShowUtils.showTips(db.tvJoin);
        DataBindingAdapter.isJoin(db.tvJoin,true);
    }
}