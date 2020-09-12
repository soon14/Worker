package com.xsd.jx.custom;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkRecommendResponse;
import com.xsd.jx.databinding.PopPushJobBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/8/21
 * author: SmallCake
 */
public class PushJobPop extends CenterPopupView {

    private BaseActivity activity;
    private List<JobBean> items;
    private int index;//获取数据位置
    private PopPushJobBinding db;
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
        db = DataBindingUtil.bind(getPopupImplView());
        getRecommend();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.tv_close:
                    dismiss();
                    break;
                case R.id.tv_share:
                    PopShowUtils.showShare(activity);
                    break;
                case R.id.tv_look_more:
                case R.id.layout_content:
                    activity.goJobInfoActivity(jobBean);
                    break;
                case R.id.tv_fav:
                    fav();
                    break;
                case R.id.tv_join:
                    join();
                    break;
                case R.id.tv_next:
                    animRota(db.layoutRoot);
                    break;
                case R.id.tv_ignore:
                    dismiss();
                    break;
            }
        });
    }

    private void join() {
        if (jobBean==null)return;
        activity.getDataProvider().work.join(jobBean.getId())
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        PopShowUtils.showTips(db.tvJoin);
                        DataBindingAdapter.isFav(db.tvJoin,true);
                    }
                });
    }

    public void fav(){
        if (jobBean==null)return;
        activity.getDataProvider().work.fav(jobBean.getId())
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        DataBindingAdapter.isFav(db.tvFav,true);
                    }
                });

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
    private JobBean jobBean;
    private void setData() {
        if (items!=null&&items.size()>0){
            if (index<items.size()){
                 jobBean = items.get(index);
                db.setItem(jobBean);
                index++;
            }else {
                //加载更多
                page++;
                getRecommend();
            }
        }

    }


    /**
     * 中心放大效果
     */
    private void animRota(View view){
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f,0f,1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f,0f,1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,0f,1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX,scaleY);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(600);
        animator.start();
        new Handler().postDelayed(() -> setData(),300);
    }

    /**
     * 卡片翻转效果
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
