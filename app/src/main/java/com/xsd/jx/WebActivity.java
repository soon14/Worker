package com.xsd.jx;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityWebviewLayoutBinding;


public class WebActivity extends BaseBindBarActivity<ActivityWebviewLayoutBinding> {

    String title;
    String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSetting();
        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("title");
            url = getIntent().getExtras().getString("url");
            tvTitle.setText(title);
            db.webView.loadUrl(url);
        }
    }

    public void initSetting() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            db.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        db.webView.getSettings().setBlockNetworkImage(false);
        db.webView.getSettings().setJavaScriptEnabled(true);
        db.webView.getSettings().setSupportZoom(false);
        db.webView.getSettings().setDomStorageEnabled(true);//开启本地DOM存储
        //解决webView加载https的网址
        db.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed();
            }
        });
    }

}
