package com.xsd.jx.custom;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.WorkRecommendResponse;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/8/21
 * author: SmallCake
 */
public class PushJobPop extends CenterPopupView {

    private List<JobBean> items;
    private TextView tvName;
    private int index;//获取数据位置
    private BaseActivity activity;
    private int page=1;
    public PushJobPop(@NonNull BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_push_job;
    }
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
        getRecommend();

    }

    //登录用户推荐工作
    private void getRecommend() {
        activity.getDataProvider().work.recommend(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkRecommendResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkRecommendResponse> baseResponse) {
                        WorkRecommendResponse data = baseResponse.getData();
                        List<JobBean> dataItems = data.getItems();
                        //如果有数据，弹框显示推荐的工作
                        if (dataItems!=null&&dataItems.size()>0){
                            items = dataItems;
                            index=0;
                            setData();
                        }else {
                            ToastUtil.showLong("没有更多数据了！");
                            dismiss();
                        }
                    }
                });
    }

    private void setData() {
        if (items!=null&&items.size()>0){
            if (index<items.size()){
                JobBean jobBean = items.get(index);
                tvName.setText(jobBean.getTypeTitle());
                index++;
            }else {
                //加载更多
                page++;
                getRecommend();
            }
        }

    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
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
                setData();

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
