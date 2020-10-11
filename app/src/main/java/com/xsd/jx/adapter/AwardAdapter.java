package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.BalanceLogResponse;
import com.xsd.jx.databinding.ItemAwardBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class AwardAdapter extends BaseQuickAdapter<BalanceLogResponse.ItemsBean, BaseDataBindingHolder<ItemAwardBinding>> implements LoadMoreModule {
    public AwardAdapter() {
        super(R.layout.item_award);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemAwardBinding> baseViewHolder, BalanceLogResponse.ItemsBean item) {
        baseViewHolder.getDataBinding().setItem(item);
    }
}
