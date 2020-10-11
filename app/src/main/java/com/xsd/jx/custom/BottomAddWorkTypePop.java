package com.xsd.jx.custom;

import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;
import com.xsd.utils.custom.AutoNewLineLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date: 2020/9/12
 * author: SmallCake
 * 从底部弹出的增加工种的弹框
 * 改为单选：工人注册以后选择工种以及去个人中心修改工种都改为只能有一个工种
 * {@link BottomSingleWorkTypePop}
 */
@Deprecated
public class BottomAddWorkTypePop extends BottomPopupView {
    private List<WorkTypeBean> items;//已经有的工种
    private BaseActivity activity;
    private OnAddWorkTypesListener listener;
    private boolean needSubmit=true;//是否需要提交工种到接口，默认为true,个人简历需要
    public interface OnAddWorkTypesListener {
        void addWorkTypes(Set<WorkTypeBean> types);
    }

    public BottomAddWorkTypePop(@NonNull BaseActivity context, List<WorkTypeBean> items,OnAddWorkTypesListener listener) {
        super(context);
        this.activity = context;
        this.items = items;
        this.listener = listener;
    }
    public BottomAddWorkTypePop(@NonNull BaseActivity context, List<WorkTypeBean> items,OnAddWorkTypesListener listener,boolean needSubmit) {
        super(context);
        this.activity = context;
        this.items = items;
        this.listener = listener;
        this.needSubmit = needSubmit;
    }


    @Override
    protected int getPopupHeight() {
        return (int) (ScreenUtils.getRealHeight()*0.96f);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_select_type_work;
    }
    AutoNewLineLayout layoutContent;
    @Override
    protected void onCreate() {
        super.onCreate();
        layoutContent = findViewById(R.id.layout_content);
        getWorkTypeList();
        findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            dismiss();
            if (tags.size()==0){
                return;
            }
            if (needSubmit){
                submit();
            }else {
                listener.addWorkTypes(tags);
            }
        });
    }

    private void submit() {
        StringBuilder builder = new StringBuilder();
        for (WorkTypeBean tag : tags) {
            int id = tag.getId();
            builder.append(id).append(",");
        }
        String s = builder.toString();
        String ids =s.substring(0,s.lastIndexOf(","));
        L.e("ids=="+ids);
        activity.getDataProvider().work.workTypeSubmitChoice(ids)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        listener.addWorkTypes(tags);
                    }
                });
    }

    private void getWorkTypeList() {
        activity.getDataProvider().work.workTypeList()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<List<WorkTypeBean>>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<WorkTypeBean>> baseResponse) {
                        List<WorkTypeBean> datas = baseResponse.getData();
                        initTypeWorks(datas);
                    }


                });
    }
    private Set<WorkTypeBean> tags = new HashSet<>();//标签，所有选中的标签项
    private void initTypeWorks(List<WorkTypeBean> datas) {
        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,DpPxUtils.dp2px(40));
        for (int i = 0; i < datas.size(); i++) {
            WorkTypeBean item = datas.get(i);
            CheckBox checkBox = new CheckBox(activity);
            checkBox.setText(item.getTitle());
            checkBox.setTextColor(ContextCompat.getColorStateList(activity, R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            layoutContent.addView(checkBox);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    tags.add(item);
                } else {
                    tags.remove(item);
                }
            });
            if (isSelect(item))checkBox.setVisibility(GONE);
        }

    }

    private boolean isSelect(WorkTypeBean item){
        if (items==null||items.size()==0)return false;
            for (int i = 0; i < items.size(); i++) {
                WorkTypeBean workTypeBean = items.get(i);
                if (item.getId()==workTypeBean.getId())return true;
            }
            return false;
    }
}
