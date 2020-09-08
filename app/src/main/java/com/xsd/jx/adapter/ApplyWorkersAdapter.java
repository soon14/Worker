package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/29
 * author: SmallCake
 * 报名工人列表适配器
 */
public class ApplyWorkersAdapter extends BaseQuickAdapter<WorkerResponse, BaseViewHolder> {
    public ApplyWorkersAdapter() {
        super(R.layout.item_getinfo_apply_workers);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WorkerResponse workerResponse) {

    }

}
