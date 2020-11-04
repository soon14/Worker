package com.xsd.jx.pop;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

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
import com.xsd.utils.ScreenUtils;
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
    private int page = 1;

    public PushJobPop(@NonNull BaseActivity context, List<JobBean> items) {
        super(context);
        this.activity = context;
        this.items = items;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_push_job;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        db = DataBindingUtil.bind(getPopupImplView());
        onEvent();
        setData();
    }

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()) {
                case R.id.tv_close:
                    dismiss();
                    break;
                case R.id.tv_share:
                    PopShowUtils.showShare(activity);
                    break;
                case R.id.tv_look_more:
                case R.id.layout_content:
                    activity.goJobInfoActivity(jobBean.getId());
                    break;
                case R.id.tv_fav:
                    fav();
                    break;
                case R.id.tv_join:
                    join();
                    break;
                case R.id.tv_next:
                    animTransY(db.layoutRoot);
                    break;
                case R.id.tv_ignore:
                    dismiss();
                    break;
            }
        });
    }

    private void join() {
        if (jobBean == null) return;
        PopShowUtils.showJoinNum(activity, new ConfirmNumPop.ConfirmListener() {
            @Override
            public void onConfirmNum(int num) {
                activity.getDataProvider().work.join(jobBean.getId(), num)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                            @Override
                            protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                PopShowUtils.showTips(activity);
                                DataBindingAdapter.isJoin(db.tvJoin, true);
                            }
                        });
            }
        });

    }

    public void fav() {
        if (jobBean == null) return;
        activity.getDataProvider().work.fav(jobBean.getId())
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        DataBindingAdapter.isFav(db.tvFav, true);
                    }
                });

    }


    //登录用户推荐工作
    boolean noMoreData;//没有更多数据了
    private void getRecommend() {
        activity.getDataProvider().work.recommend(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkRecommendResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkRecommendResponse> baseResponse) {
                        WorkRecommendResponse data = baseResponse.getData();
                        List<JobBean> dataItems = data.getItems();
                        //如果有数据，弹框显示推荐的工作
                        if (dataItems != null && dataItems.size() > 0) {
                            items.addAll(dataItems);
                            setData();
                        } else {
                            noMoreData = true;
                            index = 0;
                            setData();
                        }
                    }

                    @Override
                    protected void onErr(BaseResponse err) {
                        super.onErr(err);
                        dismiss();
                    }
                });
    }

    private JobBean jobBean;//当前工作项
    private void setData() {
        if (index < items.size()) {
            jobBean = items.get(index);
            db.setItem(jobBean);
            index++;
        } else {
            if (noMoreData) {
                index = 0;
                setData();
            } else {
                //加载更多
                page++;
                getRecommend();
            }
        }

    }

    private void animTransY(View view) {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "translationY", 0, ScreenUtils.getRealHeight());
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "translationY", -ScreenUtils.getRealHeight(), 0f);
        objectAnimator1.setDuration(500);
        objectAnimator2.setDuration(800);
        objectAnimator2.setInterpolator(new OvershootInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator1, objectAnimator2);
        animatorSet.start();
        new Handler().postDelayed(() -> setData(), 300);
    }

    @Override
    protected int getMaxWidth() {
        return (int) (XPopupUtils.getWindowWidth(getContext()) * 0.95f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.activity = null;
    }
}
