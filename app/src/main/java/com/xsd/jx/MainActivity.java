package com.xsd.jx;


import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gyf.immersionbar.ImmersionBar;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.IsInWorkResponse;
import com.xsd.jx.databinding.ActivityMainBinding;
import com.xsd.jx.fragment.JobFragment;
import com.xsd.jx.fragment.MineFragment;
import com.xsd.jx.fragment.OrderFragment;
import com.xsd.jx.job.SelectTypeWorkActivity;
import com.xsd.jx.job.SignActivity;
import com.xsd.jx.mine.RealNameAuthActivity;
import com.xsd.jx.utils.BottomNavUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.L;

/**
 * 主要包含：
 * 1.找活 {@link JobFragment}
 * 2.订单 {@link OrderFragment}
 * 3.我的 {@link MineFragment}

 列表数据处理：
 if (items!=null&&items.size()>0){
 if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
 mAdapter.getLoadMoreModule().loadMoreComplete();
 }else {
 mAdapter.getLoadMoreModule().loadMoreEnd();
 }

 如果需要刷新清空数据：
 if (items!=null&&items.size()>0){
 if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
 mAdapter.getLoadMoreModule().loadMoreComplete();
 }else {
 if (page==1)mAdapter.setList(items);else
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
        Apollo.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true).autoDarkModeEnable(true).init();
        initViewPager();

        if (UserUtils.isLogin()){
            L.e("token=="+UserUtils.getToken());
            PopShowUtils.showPushJob(this);//登录后弹框显示：推荐的工作
            if (!UserUtils.isChooseWork())goActivity(SelectTypeWorkActivity.class);//如果没有选择工种，则每次都进入工种选择页面
            isInWork();
        }
    }

    /**
     * 是否在工种中...
     */
    private void isInWork() {
        dataProvider.user.isInWork()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<IsInWorkResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<IsInWorkResponse> baseResponse) {
                        IsInWorkResponse data = baseResponse.getData();
                        boolean inWork = data.isInWork();
                        if (inWork){
                            goActivity(SignActivity.class);
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    private void initViewPager() {
        Fragment[] fragments = new Fragment[]{new JobFragment(), new OrderFragment(), new MineFragment()};
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
//        BottomNavUtils.initTabBindViewPager(db.tabLayout, db.viewPager, new OnBottomNavClickListener() {
//            @Override
////            public void onNavClick(int index) {
////                switch (index){
////                    case 1:
////                    case 2:
////                        if (!UserUtils.isLogin()){
////                            ToastUtil.showLong("请先登录！");
////                            goActivity(LoginActivity.class);
////                            new Handler().postDelayed(() -> BottomNavUtils.toDefaultTab(0,db.tabLayout,db.viewPager),300);
////                        }
////                        break;
////                }
////            }
////        });
        BottomNavUtils.initTabBindViewPager(db.tabLayout,db.viewPager,null);

    }

    @Receive(EventStr.LOGIN_OUT)
    public void loginOut(){
        BottomNavUtils.toDefaultTab(0,db.tabLayout,db.viewPager);
        goActivity(LoginActivity.class);
    }
    @Receive(EventStr.GO_LOGIN)
    public void goLoginActivity(){
        goActivity(LoginActivity.class);
    }

    @Receive(EventStr.GO_AUTH)
    public void goAuthActivity(){
        goActivity(RealNameAuthActivity.class);
    }

    @Receive(EventStr.SHOW_PUSH_JOB)
    public void showPushJob(){
        if (UserUtils.isLogin()) PopShowUtils.showPushJob(this);
    }





    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                //如果两次按键时间间隔大于2秒，则不退出
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                    //两次按键小于2秒时，退出应用
                } else {
                    finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
