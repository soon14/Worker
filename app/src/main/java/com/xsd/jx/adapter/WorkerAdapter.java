package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.ItemWorkerBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/28
 * author: SmallCake
 * 招工人首页列表适配器
 */
public class WorkerAdapter extends BaseQuickAdapter<WorkerBean, BaseDataBindingHolder<ItemWorkerBinding>> implements LoadMoreModule {
    public WorkerAdapter() {
        super(R.layout.item_worker);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemWorkerBinding> holder, WorkerBean workerResponse) {
        holder.getDataBinding().setItem(workerResponse);
    }
}
