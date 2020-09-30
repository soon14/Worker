package com.xsd.jx;

import android.os.Bundle;
import android.os.Handler;

import com.gyf.immersionbar.ImmersionBar;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.databinding.ActivityStartAdvertBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhengdaquan
 * @description:
 * @date : 2020/2/29 17:15
 */
public class StartAdvertActivity extends BaseBindActivity<ActivityStartAdvertBinding> {
    private static final String TAG = "StartAdvertActivity";

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 2000;
    private static final long AD__SHOW_TIME = 1;
    private boolean mIsExpress = false; //是否请求模板广告
    boolean isClickAd;//是否点击了广告
    Disposable timeEndDisposable;//倒计时触发器
    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_advert;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        initView();
        onEvent();

    }



    private void onEvent() {
        db.tvTime.setOnClickListener(v -> {
            if (!timeEndDisposable.isDisposed())timeEndDisposable.dispose();
            goToMainActivity();
        });
        db.ivAdvert.setOnClickListener(v -> {
        });

    }



    private void initView() {
        timeEndToMainActivity();

    }
    private void goToMainActivity() {
        new Handler().postDelayed(() -> {
            goActivity(MainActivity.class);
            finish();
        },300);

    }

    private void timeEndToMainActivity() {
        Observable.interval(1,1, TimeUnit.SECONDS)
                .take(AD__SHOW_TIME)
                .map(aLong ->AD__SHOW_TIME-aLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        timeEndDisposable = d;
                    }
                    @Override
                    public void onNext(Long aLong) {
                            db.tvTime.setText("跳过\n" + aLong + "s");
                    }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {
                        goToMainActivity();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClickAd){
            goToMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!timeEndDisposable.isDisposed())timeEndDisposable.dispose();
    }


}
