package com.xsd.jx.inject;

import android.content.Context;

import com.xsd.okhttp.retrofit2.RetrofitHttp;
import com.xsd.utils.dialog.LoadDialog;
import com.xsd.jx.api.AdvertApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Date: 2019/11/25
 * author: SmallCake
 */
@Module
public class NetWorkMoudle {
    private Context context;
    public NetWorkMoudle(Context context) {
        this.context = context;
    }
    @Singleton
    @Provides
    public Context providerContext() {
        return context;
    }
    @Singleton
    @Provides
    public LoadDialog providerLoadDialog(Context context) {
        return new LoadDialog(context);
    }
    //广告部分
    @Singleton
    @Provides
    public AdvertApi providerAdvertApi() {
        return RetrofitHttp.create(AdvertApi.class);
    }

}
