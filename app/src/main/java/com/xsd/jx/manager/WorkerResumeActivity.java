package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.databinding.ActivityWorkerResumeBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;

/**
 * 工人简历
 * type
 * 0如果是从推荐的工人列表进来的就显示【邀请上工】，
 * 1如果是从报名工人列表进来的就显示【婉拒和雇佣】，这两个按钮表现形式参考报名工人列表
 *
 WorkerBean item = (WorkerBean) adapter.getItem(position);
 Intent intent = new Intent(this, WorkerResumeActivity.class);
 intent.putExtra("type",0);
 intent.putExtra("userId",item.getUserId());
 startActivity(intent);

 */
public class WorkerResumeActivity extends BaseBindBarActivity<ActivityWorkerResumeBinding> {
    private int userId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        dataProvider.server.cv(userId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkerInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkerInfoResponse> baseResponse) {
                        WorkerInfoResponse data = baseResponse.getData();
                        db.setItem(data);

                    }
                });
    }

    private void initView() {
        tvTitle.setText("工人简历");
        int type = getIntent().getIntExtra("type", 0);
        userId = getIntent().getIntExtra("userId", 0);
        if (type==0){
            db.tvInvite.setVisibility(View.VISIBLE);
        }else if (type==1){
            db.tvRefuse.setVisibility(View.VISIBLE);
            db.tvHire.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < 2; i++) {
            View viewType = LayoutInflater.from(this).inflate(R.layout.item_type_work_del, null);
            ImageView ivDel = viewType.findViewById(R.id.iv_del);
            db.layoutTypesWork.addView(viewType);
            ivDel.setVisibility(View.GONE);
        }
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_work_history, null);
            db.layoutWorks.addView(view);
        }
    }
}