package com.xsd.jx;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.base.Contants;
import com.xsd.jx.utils.LogHeaderInterceptor;
import com.xsd.okhttp.retrofit2.RetrofitHttp;
import com.xsd.utils.SmallUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmallUtils.init(this);//工具类
        RetrofitHttp.init(this, Contants.BASE_URL, new LogHeaderInterceptor());//自定义拦截器，方便添加公共Header
        Apollo.init(AndroidSchedulers.mainThread(), this);//事件通知
    }
    //方法数量过多，合并
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
