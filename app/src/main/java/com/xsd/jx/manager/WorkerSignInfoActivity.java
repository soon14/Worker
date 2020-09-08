package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityWorkerSignInfoBinding;
import com.xsd.utils.ToastUtil;

/**
 * 记录明细
 */
public class WorkerSignInfoActivity extends BaseBindBarActivity<ActivityWorkerSignInfoBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.tv_confirm_sign:
                        showConfrimSign();
                        break;
                }
            }
        });

    }

    private void initView() {
        tvTitle.setText("记录明细");

    }

    private void showConfrimSign() {
        new XPopup.Builder(this)
                .asConfirm("修改结果",
                        "徐冬冬 2020-08-28 属于上工期间，当天未完成考勤，您是否确定将TA的考勤记录修改为正常？",
                        "暂不修改",
                        "确定",
                        () ->{
                            ToastUtil.showLong("已改为正常！");
                            db.tvConfirmSign.setText("已确认考勤");
                            db.tvConfirmSign.setTextColor(ContextCompat.getColor(this,R.color.tv_gray));
                            db.tvConfirmSign.setBackgroundResource(R.drawable.round6_gray_bg);
                        },
                        null,
                        false,R.layout.dialog_tips)
                .show();

    }
}