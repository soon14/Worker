package com.xsd.jx.utils;


import android.app.Dialog;
import android.text.TextUtils;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.load.HttpException;
import com.google.gson.internal.LinkedTreeMap;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;
import com.xsd.jx.bean.BaseErrResponse;
import com.xsd.jx.bean.BaseResponse;

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
        if (t instanceof String) {
            JSONObject object = JSONObject.parseObject((String) t);
            if (object.containsKey("success")) {
                boolean success = object.getBoolean("success");
                if (success) {
                    onSuccess(t);
                } else {
                    BaseErrResponse errResponse = AppJsonUtil.getObject((String) t, BaseErrResponse.class);
                    onErr(errResponse);
                }
            }
        } else {

            try {
                BaseResponse baseResponse = (BaseResponse) t;
                if (baseResponse.getSuccess()) {
                    onSuccess(t);
                } else {
                    Object dataErr = baseResponse.getData();
                    BaseErrResponse errResponse = null;
                    if (dataErr instanceof BaseErrResponse) {
                        errResponse = (BaseErrResponse) dataErr;
                    } else if (dataErr instanceof LinkedTreeMap) {
                        LinkedTreeMap data = (LinkedTreeMap) dataErr;
                        String codeStr = data.get("code").toString();
                        String statusStr = data.get("status").toString();
                        String code = codeStr.substring(0, codeStr.indexOf("."));
                        String status = statusStr.substring(0, statusStr.indexOf("."));
                        errResponse = new BaseErrResponse.Builder()
                                .name((String) data.get("name"))
                                .message((String) data.get("message"))
                                .code(Integer.parseInt(code))
                                .status(Integer.parseInt(status)).build();
                    }
                    L.e(TAG,"错误==" + errResponse.toString());
                    onErr(errResponse);
                }
            } catch (Exception e) {
                onError(e);
            }
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

    protected void onErr(BaseErrResponse err) {
        L.e(TAG,"网络数据异常：" + err.toString());
        switch (err.getStatus()) {
            case 401://跳登录
                break;
            default:
                ToastUtil.showLong(err.getMessage());
                break;
        }

    }
}

