package com.xsd.jx;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.lsxiao.apollo.core.Apollo;
import com.lzf.easyfloat.EasyFloat;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xsd.jx.base.Contants;
import com.xsd.jx.base.MyOSSConfig;
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
        //工具集
        SmallUtils.init(this);
        //网络框架
        RetrofitHttp.init(this, Contants.BASE_URL, new LogHeaderInterceptor());
        //事件通知
        Apollo.init(AndroidSchedulers.mainThread(), this);
        //阿里云存储
        initAliOSS();
        //友盟分享，统计
        UMConfigure.init(this, Contants.UM_APP_KEY,"xsd", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //全局悬浮球
        EasyFloat.init(this, BuildConfig.DEBUG);
        //生命周期监听
        registLifecycle();

    }

    private void registLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
            @Override
            public void onActivityStarted(Activity activity) {}
            @Override
            public void onActivityResumed(Activity activity) {
                ActivityCollector.getInstance().setCurrentActivity(activity);
            }
            @Override
            public void onActivityPaused(Activity activity) {}
            @Override
            public void onActivityStopped(Activity activity) {}
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override
            public void onActivityDestroyed(Activity activity) {}
        });
    }

    public static MyApplication getInstance() {
        return instances;
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
