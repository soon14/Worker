package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.BalanceLogResponse;
import com.xsd.jx.databinding.ItemPaymentsBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class PaymentsAdapter extends BaseQuickAdapter<BalanceLogResponse.ItemsBean, BaseDataBindingHolder<ItemPaymentsBinding>> implements LoadMoreModule {
    public PaymentsAdapter() {
        super(R.layout.item_payments);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemPaymentsBinding> holder, BalanceLogResponse.ItemsBean item) {
        holder.getDataBinding().setItem(item);
        holder.setText(R.id.tv_price,item.getValue()+"元/"+item.getType());
    }
}
