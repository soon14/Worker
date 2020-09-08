package com.xsd.jx.custom;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;

/**
 * Date: 2020/8/21
 * author: SmallCake
 */
public class PushJobPop extends CenterPopupView {
    public PushJobPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_push_job;
    }
    TextView tvName;
    @Override
    protected void onCreate() {
        super.onCreate();
         tvName = findViewById(R.id.tv_name);
        View layoutRoot = findViewById(R.id.layout_root);
        findViewById(R.id.tv_next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animRota(layoutRoot);
            }
        });


    }
    private void animRota(View view){
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f,0f,1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f,0f,1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,0f,1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX,scaleY);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(800);
        animator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = tvName.getText().toString();
                tvName.setText(s.equals("木工")?"电工":"木工");

            }
        },400);
    }

    /**
     * 卡片翻转效果
     * @param view
     * @return
     */
    public static ObjectAnimator rotateAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotationY",0,360f);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(8000);
        animator.start();
        return animator;
    }

    @Override
    protected int getMaxWidth() {
        return (int) (XPopupUtils.getWindowWidth(getContext()) * 0.918f);
    }
    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.918f);
    }
}
