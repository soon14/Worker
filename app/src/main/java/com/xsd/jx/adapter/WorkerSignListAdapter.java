package com.xsd.jx.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.databinding.ItemWorkerSignListBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/31
 * author: SmallCake
 * 1.有上工时间&&未确认： 确认考勤按钮
 * 2.有上工时间&&已确认： 已考勤
 * 3.无上工时间： 未考勤
 */
public class WorkerSignListAdapter extends BaseQuickAdapter<WorkCheckLogResponse.ItemsBean, BaseDataBindingHolder<ItemWorkerSignListBinding>> {
    public WorkerSignListAdapter() {
        super(R.layout.item_worker_sign_list);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemWorkerSignListBinding> holder, WorkCheckLogResponse.ItemsBean item) {
        ItemWorkerSignListBinding binding = holder.getDataBinding();
        binding.setItem(item);
        String signInTime = item.getSignInTime();
        int status = item.getStatus();//确认状态 0:未确认 1:已确认
        holder.setText(R.id.tv_time, TextUtils.isEmpty(signInTime)?"未考勤":"上工时间:"+signInTime);
        holder.setText(R.id.tv_status,TextUtils.isEmpty(signInTime)?"未考勤":"已考勤");
        binding.tvConfirmSign.setVisibility((!TextUtils.isEmpty(signInTime)&&status==0)? View.VISIBLE:View.GONE);
        binding.tvStatus.setVisibility((!TextUtils.isEmpty(signInTime)&&status==0)? View.GONE:View.VISIBLE);
    }
}
