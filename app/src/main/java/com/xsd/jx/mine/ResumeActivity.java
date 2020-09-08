package com.xsd.jx.mine;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityResumeBinding;
import com.xsd.utils.ScreenUtils;

/**
 * 个人简历
 */
public class ResumeActivity extends BaseBindBarActivity<ActivityResumeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("个人简历");
        for (int i = 0; i < 5; i++) {
            View viewType = LayoutInflater.from(this).inflate(R.layout.item_type_work_del, null);
            ImageView ivDel = viewType.findViewById(R.id.iv_del);
            db.layoutTypesWork.addView(viewType);
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewType.animate()
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    db.layoutTypesWork.removeView(viewType);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).translationX(ScreenUtils.getRealWidth()).alpha(0f).start();
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_work_history, null);
            db.layoutWorks.addView(view);
        }
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.layout_edit:
                    goActivity(EditIntroActivity.class);
                    break;
            }
        });
    }
}