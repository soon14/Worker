package com.xsd.jx.custom;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.utils.SpannableStringUtils;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class BottomSharePop extends BottomPopupView {
    public BottomSharePop(@NonNull Context context) {
        super(context);
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

    }
}
