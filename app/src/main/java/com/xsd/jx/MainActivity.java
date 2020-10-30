package com.xsd.jx;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.base.MyOSSConfig;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.InviteListResponse;
import com.xsd.jx.bean.IsInWorkResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.StsResponse;
import com.xsd.jx.databinding.ActivityMainBinding;
import com.xsd.jx.fragment.JobFragment;
import com.xsd.jx.fragment.MineFragment;
import com.xsd.jx.fragment.OrderFragment;
import com.xsd.jx.job.SelectTypeWorkActivity;
import com.xsd.jx.job.SignActivity;
import com.xsd.jx.utils.BottomNavUtils;
import com.xsd.jx.utils.CommonDataUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.ActivityCollector;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

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
    private static final String TAG = "MainActivity";
    private String[] tabNames = new String[]{"找活","订单","我的"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Apollo.isBind(this)) Apollo.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true).autoDarkModeEnable(true).init();
        initViewPager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserUtils.isLogin()){
                    if (UserUtils.isCertification()){
                        if (UserUtils.isChooseWork())PopShowUtils.showPushJob(MainActivity.this);//登录后弹框显示：推荐的工作
                        isInWork();//是否在工期中
                        getInviteList();//邀请工作
                    }
                    if (!UserUtils.isChooseWork())goActivity(SelectTypeWorkActivity.class);//如果没有选择工种，则每次都进入工种选择页面
                }
                PopShowUtils.showAppUpdate(MainActivity.this);
                CommonDataUtils.getPhone(MainActivity.this);
                //初始化一个OSSClient客户端，方便打卡操作更快捷
                initOssClient();
            }
        },2000);
        PopShowUtils.showConfirmEmployNum(this);
    }

    private void initOssClient() {
        dataProvider.user.aliSts()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<StsResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<StsResponse> baseResponse) {
                        StsResponse data = baseResponse.getData();
                        String accessKeyId = data.getAccessKeyId();
                        String accessKeySecret = data.getAccessKeySecret();
                        String securityToken = data.getSecurityToken();
                        ClientConfiguration conf = new ClientConfiguration();
                        conf.setHttpDnsEnable(true);
                        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken);
                        new Thread(() -> {
                            new OSSClient(MainActivity.this, MyOSSConfig.ENDPOINT, credentialProvider,conf);
                        }).start();

                    }
                });
    }

    private void openBall(int count, List<JobBean> data) {
        XXPermissions.with(this)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all){
                            EasyFloat.dismissAppFloat();
                            EasyFloat.with(MainActivity.this)
                                    .setShowPattern(ShowPattern.FOREGROUND)
                                    .setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT,0, ScreenUtils.getRealHeight()/2- DpPxUtils.dp2px(88))
                                    .setSidePattern(SidePattern.RESULT_HORIZONTAL)
                                    .setLayout(R.layout.layout_ball, new OnInvokeView() {
                                        @Override
                                        public void invoke(View view) {
                                            TextView tv = view.findViewById(R.id.tv_invite);
                                            tv.setText(count+"个\n邀请");
                                            view.setOnClickListener(v -> {
                                                Activity currentActivity = ActivityCollector.getInstance().getCurrentActivity();
                                                PopShowUtils.showInviteJob(data, (BaseActivity) currentActivity);
                                            });
                                        }
                                    })
                                    .show();
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.showLong("请开启悬浮窗权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, denied);
                        } else {
                            ToastUtil.showLong("开启悬浮窗权限失败");
                        }
                    }
                });
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
        EasyFloat.dismissAppFloat();
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
        BottomNavUtils.initTabBindViewPager(db.tabLayout,db.viewPager,null);

    }



    @Receive(EventStr.LOGIN_OUT)
    public void loginOut(){
        L.e(TAG,"loginOut()===");
        BottomNavUtils.toDefaultTab(0,db.tabLayout,db.viewPager);
        goActivity(LoginActivity.class);
        EasyFloat.dismissAppFloat();
    }
    @Receive(EventStr.GO_LOGIN)
    public void goLoginActivity(){
        goActivity(LoginActivity.class);
    }

    @Receive(EventStr.GO_AUTH)
    public void goAuthActivity(){
        PopShowUtils.showRealNameAuth(this);
    }

    @Receive(EventStr.SHOW_PUSH_JOB)
    public void showPushJob(){
        if (UserUtils.isLogin()&&UserUtils.isCertification()){
            PopShowUtils.showPushJob(this);
            isInWork();//是否在工期中
        }

    }
    //被邀请上工信息列表
    @Receive(EventStr.UPDATE_INVITE_LIST)
    public void getInviteList() {
        dataProvider.work.inviteList()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<InviteListResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<InviteListResponse> baseResponse) {
                        InviteListResponse response = baseResponse.getData();
                        List<JobBean> data = response.getItems();
                        int count = response.getCount();
                        if (data!=null&&data.size()>0){
                            openBall(count,data);
                        }else {
                           EasyFloat.dismissAppFloat();
                        }
                    }
                });
    }
    @Receive(EventStr.LOGIN_SUCCESS)
    public void loginSuccess(){
        showPushJob();
        getInviteList();
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
