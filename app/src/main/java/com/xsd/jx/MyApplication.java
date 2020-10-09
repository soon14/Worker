package com.xsd.jx;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.lsxiao.apollo.core.Apollo;
import com.lzf.easyfloat.EasyFloat;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xsd.jx.base.Contants;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.base.MyOSSConfig;
import com.xsd.jx.manager.GetWorkersActivity;
import com.xsd.jx.utils.LogHeaderInterceptor;
import com.xsd.okhttp.retrofit2.RetrofitHttp;
import com.xsd.utils.ActivityCollector;
import com.xsd.utils.SmallUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class MyApplication extends Application {
    private static MyApplication instances;
    private int liveActivityCount;//当前应用活动的页面个数
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        SmallUtils.init(this);//工具类
        RetrofitHttp.init(this, Contants.BASE_URL, new LogHeaderInterceptor());//自定义拦截器，方便添加公共Header
        Apollo.init(AndroidSchedulers.mainThread(), this);//事件通知
        initAliOSS();
        UMConfigure.init(this, Contants.UM_APP_KEY,"xsd", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        EasyFloat.init(this, BuildConfig.DEBUG);
        isBackEvent();
    }
    public static MyApplication getInstance() {
        return instances;
    }

    private void isBackEvent() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}
            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                liveActivityCount++;
                boolean b = ActivityCollector.hasActivity(GetWorkersActivity.class.getSimpleName());
                   if (!b)Apollo.emit(EventStr.SHOW_RECOMMEND_JOB_POP);
            }
            @Override
            public void onActivityResumed(@NonNull Activity activity) {}
            @Override
            public void onActivityPaused(@NonNull Activity activity) {}
            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                liveActivityCount--;
                if (liveActivityCount==0)Apollo.emit(EventStr.HIDE_RECOMMEND_JOB_POP);
            }
            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}
            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {}
        });
    }

    //方法数量过多，合并
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private OSS oss;
    public OSS getOss() {
        return oss;
    }
    private void initAliOSS() {
        // 配置类如果不设置，会有默认配置。
        OSSPlainTextAKSKCredentialProvider provider = new OSSPlainTextAKSKCredentialProvider(MyOSSConfig.OSS_ACCESS_KEY_ID, MyOSSConfig.OSS_ACCESS_KEY_SECRET);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        new Thread(() -> oss = new OSSClient(MyApplication.getInstance(), MyOSSConfig.OSS_EXTERNAL_ENDPOINT, provider, conf)).start();
    }
}
