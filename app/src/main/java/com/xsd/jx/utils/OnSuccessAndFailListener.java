package com.xsd.jx.utils;


import android.app.Dialog;
import android.text.TextUtils;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.HttpException;
import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;


/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public abstract class OnSuccessAndFailListener<T> extends DisposableObserver<T> {
    private static final String TAG = "OnSuccessAndFailListene";
    private Dialog loadDialog;

    private SwipeRefreshLayout androidRefresh;
    private void showLoading() {
        if (loadDialog != null)loadDialog.show();
        if (androidRefresh!=null)androidRefresh.setRefreshing(true);
    }

    private void dismissLoading() {
        if (loadDialog != null){
            loadDialog.dismiss();
        }
        if (androidRefresh!=null)androidRefresh.setRefreshing(false);
    }

    public OnSuccessAndFailListener() {
    }

    public OnSuccessAndFailListener(Dialog loadDialog) {
        this.loadDialog = loadDialog;
    }

    public OnSuccessAndFailListener(SwipeRefreshLayout refresh) {
        this.androidRefresh = refresh;
    }


    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
    }

    @Override
    public void onNext(T t) {
        dismissLoading();
        try {
            BaseResponse baseResponse = (BaseResponse) t;
            if (baseResponse.getCode()==0){
                onSuccess(t);
            }else {

                //20103,未登录或登录过期
                if (baseResponse.getCode()==20103){
                    ToastUtil.showLong("请先登录！");
                    Apollo.emit(EventStr.GO_LOGIN);
                }else if (baseResponse.getCode()==20105){
                    ToastUtil.showLong("请先实名认证！");
                    Apollo.emit(EventStr.GO_AUTH);
                }else {
                    onErr(baseResponse.getMessage());
                }

            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissLoading();
//        e.printStackTrace();
        String message;
        if (e instanceof SocketTimeoutException) {
            message = "SocketTimeoutException:网络连接超时！";
        } else if (e instanceof ConnectException) {
            message = "ConnectException:网络无法连接！";
        } else if (e instanceof HttpException) {
            message = "HttpException:网络中断，请检查您的网络状态！";
        } else if (e instanceof UnknownHostException) {
            message = "UnknownHostException:网络错误，请检查您的网络状态！";
        } else {
            message = e.getMessage();
        }
        L.e(TAG,"onError=="+message);
        if (!TextUtils.isEmpty(message))ToastUtil.showLong(message);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    protected void onErr(String err) {
        ToastUtil.showLong(err);
        L.e(TAG,"网络数据异常：" + err.toString());
    }
}

