package com.xsd.jx.job;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityJobInfoBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.ToastUtil;

/**
 * 详细招工信息
 */
public class JobInfoActivity extends BaseBindBarActivity<ActivityJobInfoBinding> {

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

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.tv_fav:
                    fav();
                    break;
                case R.id.tv_share:
                    PopShowUtils.showShare(JobInfoActivity.this);
                    break;
                case R.id.tv_join:
                    join();
                    break;
            }
        });

    }
    private int id;
    private void initView() {
        JobBean item = (JobBean) getIntent().getSerializableExtra("JobBean");
        if (item==null){
            ToastUtil.showLong("数据为空！");
            finish();
            return;
        }
        id = item.getId();
        db.setItem(item);

    }
    public void fav(){
        dataProvider.work.fav(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        DataBindingAdapter.isFav(db.tvFav,true);
                    }
                });

    }
    private void join() {
        dataProvider.work.join(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        PopShowUtils.showTips(db.tvJoin);
                        DataBindingAdapter.isJoin(db.tvJoin,true);
                    }
                });
    }
}