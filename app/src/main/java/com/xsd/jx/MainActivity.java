package com.xsd.jx;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.custom.PushJobPop;
import com.xsd.jx.databinding.ActivityMainBinding;
import com.xsd.jx.fragment.JobFragment;
import com.xsd.jx.fragment.MineFragment;
import com.xsd.jx.fragment.OrderFragment;
import com.xsd.jx.job.SelectTypeWorkActivity;
import com.xsd.jx.listener.OnBottomNavClickListener;
import com.xsd.jx.utils.BottomNavUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.L;

/**
 * 主要包含：
 * 1.找活 {@link JobFragment}
 * 2.订单 {@link OrderFragment}
 * 3.我的 {@link MineFragment}
 if (items!=null&&items.size()>0){
 if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
 mAdapter.getLoadMoreModule().loadMoreComplete();
 }else {
 mAdapter.getLoadMoreModule().loadMoreEnd();
 }
 */

public class MainActivity extends BaseBindActivity<ActivityMainBinding> {
    private String[] tabNames = new String[]{"找活", "订单", "我的"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarDarkFont(true).autoDarkModeEnable(true).init();
        initViewPager();
//        showPushJob();

        getWorkTypeList();//如果没有选择工种，则每次都弹窗工种选择
        L.e("token=="+ UserUtils.getToken());
//        getRecommend();
    }

    private void getRecommend() {
        dataProvider.work.recommend()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                    }
                });
    }

    private void getWorkTypeList() {
        if (!UserUtils.isChooseWork())goActivity(SelectTypeWorkActivity.class);
    }


    private void showPushJob() {
        new XPopup.Builder(this)
                .asCustom(new PushJobPop(this))
                .show();
    }

    private void initViewPager() {
        final Fragment[] fragments = new Fragment[]{new JobFragment(), new OrderFragment(), new MineFragment()};
        final FragmentStatePagerAdapter fragmentPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager(), 0) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @NonNull
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames[position];
            }
        };
        db.viewPager.setAdapter(fragmentPagerAdapter);
        db.viewPager.setOffscreenPageLimit(tabNames.length);
        BottomNavUtils.initTabBindViewPager(db.tabLayout, db.viewPager, new OnBottomNavClickListener() {
            @Override
            public void onNavClick(int index) {

            }
        });

    }


}
