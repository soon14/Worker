package com.xsd.jx.adapter;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ItemWorkerSignListBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/31
 * author: SmallCake
 */
public class WorkerSignListAdapter extends BaseQuickAdapter<WorkerResponse, BaseDataBindingHolder<ItemWorkerSignListBinding>> {
    public WorkerSignListAdapter() {
        super(R.layout.item_worker_sign_list);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemWorkerSignListBinding> holder, WorkerResponse workerResponse) {
        ItemWorkerSignListBinding dataBinding = holder.getDataBinding();

        int itemType = workerResponse.getItemType();
        switch (itemType){
            case 0://已考勤
                dataBinding.tvConfirmSign.setVisibility(View.GONE);
                dataBinding.tvStatus.setVisibility(View.VISIBLE);
                dataBinding.tvStatus.setText("已考勤");
                dataBinding.tvStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_gray));
                break;
            case 1://未考勤
                dataBinding.tvConfirmSign.setVisibility(View.GONE);
                dataBinding.tvStatus.setVisibility(View.VISIBLE);
                dataBinding.tvStatus.setText("未考勤");
                dataBinding.tvStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
                break;
            case 2://确认考勤
                dataBinding.tvConfirmSign.setVisibility(View.VISIBLE);
                dataBinding.tvStatus.setVisibility(View.GONE);
                break;
        }
    }
}
