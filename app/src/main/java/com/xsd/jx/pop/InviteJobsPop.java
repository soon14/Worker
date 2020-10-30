package com.xsd.jx.pop;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.databinding.ItemJobListBinding;
import com.xsd.jx.listener.OnJobSelectListener;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.SpannableStringUtils;

import java.util.List;

/**
 * Date: 2020/9/7
 * author: SmallCake
 */
public class InviteJobsPop extends CenterPopupView {
    private List<JobListResponse.ItemsBean> items;
    private int selectIndex;
    private OnJobSelectListener listener;

    public void setListener(OnJobSelectListener listener) {
        this.listener = listener;
    }

    public InviteJobsPop(@NonNull Context context, List<JobListResponse.ItemsBean> items) {
        super(context);
        this.items=items;
    }

    @Override
    protected int getPopupHeight() {
        return (int) (ScreenUtils.getRealHeight()*0.9f);
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
        for (int i = 0; i < items.size(); i++) {
            JobListResponse.ItemsBean item = items.get(i);
            View viewChild = LayoutInflater.from(this.getContext()).inflate(R.layout.item_job_list, null);
            ItemJobListBinding bind = DataBindingUtil.bind(viewChild);
            bind.setItem(item);
            bind.tvPrice.setText(item.getTypeTitle()+"/"+item.getPrice()+"元/天");
            TextView tvInviteNum = viewChild.findViewById(R.id.tv_invite_num);
            SpannableStringBuilder spannableStringBuilder = SpannableStringUtils.getBuilder("还差")
                    .append(item.getSurplus()+"").setForegroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent)).setBold()
                    .append("人招满")
                    .create();
            tvInviteNum.setText(spannableStringBuilder);

            layoutContent.addView(viewChild);
        }
        for (int i = 0; i < items.size(); i++) {
            LinearLayout viewChild = (LinearLayout) layoutContent.getChildAt(i);
            CheckBox cbSelect = viewChild.findViewById(R.id.cb_select);
            int finalI = i;
            cbSelect.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b){
                    selectIndex=finalI;
                    clearOtherCheckBox(layoutContent);
                }
            });
        }
        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener!=null)listener.onSelect(items.get(selectIndex));

            }
        });
    }

    private void clearOtherCheckBox(LinearLayout layoutContent){
        for (int i = 0; i < items.size(); i++) {
            LinearLayout viewChild = (LinearLayout) layoutContent.getChildAt(i);
            CheckBox cbSelect = viewChild.findViewById(R.id.cb_select);
            boolean checked = cbSelect.isChecked();
            if (checked&&i!=selectIndex) cbSelect.setChecked(false);
        }
    }
}
