package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.custom.BottomSharePop;
import com.xsd.jx.custom.InviteJobPop;
import com.xsd.jx.databinding.FragmentJobBinding;
import com.xsd.jx.job.JobInfoActivity;
import com.xsd.jx.job.JobPriceInquireActivity;
import com.xsd.jx.job.PermanentWorkerActivity;
import com.xsd.jx.job.SignActivity;
import com.xsd.jx.mine.RealNameAuthActivity;
import com.xsd.jx.utils.BannerUtils;
import com.xsd.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 找活
 */
public class JobFragment extends BaseBindFragment<FragmentJobBinding> {
    private static final String TAG = "JobFragment";
    private JobAdapter mAdapter = new JobAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {
        L.e(TAG,"onBindView");

    }

    @Override
    protected void onLazyLoad() {
        L.e(TAG,"onLazyLoad");
        initView();
        onEvent();
        loadData();
    }

    private void initView() {
        //轮播图片
        List<Object> imgs = new ArrayList<>();
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        BannerUtils.initBanner(db.banner,imgs);
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db.recyclerView.setAdapter(mAdapter);

        //添加头部提示
        TextView tvNotice = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tv_notice, null);
        mAdapter.addHeaderView(tvNotice);

        List<JobBean> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new JobBean());
        }
        mAdapter.setList(datas);
    }
    //网络请求
    private void loadData() {
    }

    private void onEvent() {
        int childCount = db.layoutTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            db.layoutTab.getChildAt(i).setOnClickListener(v -> {
                switch (finalI){
                    case 0://查工价
                        goActivity(JobPriceInquireActivity.class);
                        break;
                    case 1://突击工
                        goActivity(PermanentWorkerActivity.class,0);
                        break;
                    case 2://长期工
                        goActivity(PermanentWorkerActivity.class,1);
                        break;
                    case 3:
                        goActivity(SignActivity.class);
                        break;
                    case 4:
//                        goActivity(LoginActivity.class);
                        showShare();
                        break;
                }
            });
        }
        mAdapter.addChildClickViewIds(R.id.tv_apply);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                showRealNameAuth(view);
//                showTips(view);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                goActivity(JobInfoActivity.class);
            }
        });
        db.tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInviteJob();
            }
        });

    }

    private void showShare() {
        new XPopup.Builder(this.getActivity())
                .asCustom(new BottomSharePop(this.getActivity()))
                .show();
    }
    private void showInviteJob() {
        new XPopup.Builder(this.getActivity())
                .asCustom(new InviteJobPop(this.getActivity()))
                .show();
    }

    private void showTips(View v) {
        new XPopup.Builder(v.getContext())
                .asConfirm("报名上工提醒",
                        "您已成功报名上工，请耐心等待企业确认您的上工申请，企业确认后您才可有效上工。",
                        "取消",
                        "知道了",
                        null,
                        null,
                        true,
                        R.layout.dialog_tips)
                .show();
    }
    private void showRealNameAuth(View v) {
        new XPopup.Builder(v.getContext())
                .asConfirm("实名认证提醒",
                        "根据国家政策规定，您需要先完成实名认证才可上工。",
                        "取消",
                        "实名认证提醒",
                        () -> goActivity(RealNameAuthActivity.class),
                        null,
                        true,R.layout.dialog_tips)
                .show();

    }

}
