package com.xsd.jx;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.base.Contants;
import com.xsd.jx.base.MyOSSConfig;
import com.xsd.jx.utils.LogHeaderInterceptor;
import com.xsd.okhttp.retrofit2.RetrofitHttp;
import com.xsd.utils.SmallUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class MyApplication extends Application {
    public static MyApplication instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        SmallUtils.init(this);//工具类
        RetrofitHttp.init(this, Contants.BASE_URL, new LogHeaderInterceptor());//自定义拦截器，方便添加公共Header
        Apollo.init(AndroidSchedulers.mainThread(), this);//事件通知
        initAliOSS();
    }
    //方法数量过多，合并
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private OSS oss;

    public static MyApplication getInstance() {
        return instances;
    }

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                oss = new OSSClient(MyApplication.getInstance(), MyOSSConfig.OSS_EXTERNAL_ENDPOINT, provider, conf);
            }
        }).start();
    }
}
