package com.xsd.jx.custom;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.utils.ShareUtils;
import com.xsd.utils.SpannableStringUtils;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class BottomSharePop extends BottomPopupView {
    BaseActivity activity;
    public BottomSharePop(@NonNull BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_share;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        TextView tvDesc = findViewById(R.id.tv_desc);
        SpannableStringBuilder spanStr = SpannableStringUtils.getBuilder("工友点击您分享的链接，成功注册您立得")
                .append("5元现金").setForegroundColor(Color.RED).setBold()
                .append("，若工友在平台完成上工，您每次都可再获得现金奖励。")
                .create();
        tvDesc.setText(spanStr);

        findViewById(R.id.tv_wx)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareUtils.shareText(activity,"分享","分享","若工友在平台完成上工，您每次都可再获得现金奖励。");
                    }
                });
        findViewById(R.id.tv_wx_circle)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareUtils.shareText(activity,"分享","分享","若工友在平台完成上工，您每次都可再获得现金奖励。");
                    }
                });

    }


}
