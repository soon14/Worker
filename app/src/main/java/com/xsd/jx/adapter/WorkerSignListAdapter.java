package com.xsd.jx.adapter;

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
 *
 * 状态：
 * 1.checkId考勤ID，该ID为0的情况表示未考勤
 * 2.status	integer
 * 确认状态 1:未确认 2:已确认
 */
public class WorkerSignListAdapter extends BaseQuickAdapter<WorkCheckLogResponse.ItemsBean, BaseDataBindingHolder<ItemWorkerSignListBinding>> {
    public WorkerSignListAdapter() {
        super(R.layout.item_worker_sign_list);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemWorkerSignListBinding> holder, WorkCheckLogResponse.ItemsBean item) {
        ItemWorkerSignListBinding binding = holder.getDataBinding();
        binding.setItem(item);
        int checkId = item.getCheckId();

        String signInTime = item.getSignInTime();
        int status = item.getStatus();//确认状态 1:未确认 2:已确认
        holder.setText(R.id.tv_time, checkId==0?"未考勤":"上工时间:"+signInTime);
        holder.setText(R.id.tv_status,checkId==0?"未考勤":"已考勤");
        binding.tvConfirmSign.setVisibility((checkId>0&&status==1)? View.VISIBLE:View.GONE);
        binding.tvStatus.setVisibility((checkId>0&&status==1)? View.GONE:View.VISIBLE);
    }
}
