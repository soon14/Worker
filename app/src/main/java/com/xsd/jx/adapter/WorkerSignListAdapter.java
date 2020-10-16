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
        String signOutTime = item.getSignOutTime();
        int status = item.getStatus();//确认状态 1:未确认 2:已确认
        holder.setText(R.id.tv_time, checkId==0?"未考勤":(TextUtils.isEmpty(signOutTime)?"上工时间:"+signInTime:"上工:"+signInTime+"下工:"+signOutTime));
        holder.setText(R.id.tv_status,checkId==0?"未考勤":(TextUtils.isEmpty(signOutTime)?"未打下班卡":"已考勤"));
        //是否应该显示确认考勤
        boolean showConfirmBtn = checkId > 0 && status == 1 && !TextUtils.isEmpty(signInTime) && !TextUtils.isEmpty(signOutTime);
        binding.tvConfirmSign.setVisibility(showConfirmBtn? View.VISIBLE:View.GONE);
        binding.tvStatus.setVisibility(showConfirmBtn? View.GONE:View.VISIBLE);
    }
}
