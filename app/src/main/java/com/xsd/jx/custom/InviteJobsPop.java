package com.xsd.jx.custom;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.SpannableStringUtils;

/**
 * Date: 2020/9/7
 * author: SmallCake
 */
public class InviteJobsPop extends CenterPopupView {
    public InviteJobsPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getPopupHeight() {
        return (int) (ScreenUtils.getRealHight()*0.9f);
    }


    protected int getMaxWidth() {
        return popupInfo.maxWidth==0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.96f)
                : popupInfo.maxWidth;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_invite_jobs;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_title).setOnClickListener(view -> dismiss());
        LinearLayout layoutContent = findViewById(R.id.layout_content);
        for (int i = 0; i < 6; i++) {
            View viewChild = LayoutInflater.from(this.getContext()).inflate(R.layout.item_jobs, null);
            TextView tvInviteNum = viewChild.findViewById(R.id.tv_invite_num);
            SpannableStringBuilder spannableStringBuilder = SpannableStringUtils.getBuilder("还差")
                    .append("1").setForegroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent)).setBold()
                    .append("人招满")
                    .create();
            tvInviteNum.setText(spannableStringBuilder);
            layoutContent.addView(viewChild);
        }
        for (int i = 0; i < 6; i++) {
            LinearLayout viewChild = (LinearLayout) layoutContent.getChildAt(i);
            CheckBox cbSelect = viewChild.findViewById(R.id.cb_select);
            int finalI = i;
            cbSelect.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b)clearOtherCheckBox(layoutContent, finalI);
            });
        }
    }

    private void clearOtherCheckBox( LinearLayout layoutContent,int index){
        for (int i = 0; i < 6; i++) {
            LinearLayout viewChild = (LinearLayout) layoutContent.getChildAt(i);
            CheckBox cbSelect = viewChild.findViewById(R.id.cb_select);
            boolean checked = cbSelect.isChecked();
            if (checked&&i!=index) cbSelect.setChecked(false);
        }
    }
}
