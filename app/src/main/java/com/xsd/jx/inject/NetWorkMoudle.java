package com.xsd.jx.inject;

import android.content.Context;

import com.xsd.jx.api.OrderApi;
import com.xsd.jx.api.ServerApi;
import com.xsd.jx.api.SiteApi;
import com.xsd.jx.api.UserApi;
import com.xsd.jx.api.WorkApi;
import com.xsd.okhttp.retrofit2.RetrofitHttp;
import com.xsd.utils.dialog.LoadDialog;

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

    @Singleton
    @Provides
    public SiteApi providerSiteApi() {
        return RetrofitHttp.create(SiteApi.class);
    }

    @Singleton
    @Provides
    public WorkApi providerWorkApi() {
        return RetrofitHttp.create(WorkApi.class);
    }

    @Singleton
    @Provides
    public OrderApi providerOrderApi() {
        return RetrofitHttp.create(OrderApi.class);
    }

    @Singleton
    @Provides
    public UserApi providerUserApi() {
        return RetrofitHttp.create(UserApi.class);
    }

    /**
     * ========================================================【企业端】=============================================================================
     */
    @Singleton
    @Provides
    public ServerApi providerServerApi() {
        return RetrofitHttp.create(ServerApi.class);
    }

}
