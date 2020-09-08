package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/28
 * author: SmallCake
 * 招工人首页列表适配器
 */
public class WorkerAdapter extends BaseQuickAdapter<WorkerResponse, BaseViewHolder> {
    public WorkerAdapter() {
        super(R.layout.item_worker);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WorkerResponse workerResponse) {

    }
}
