package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.BalanceLogResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class PaymentsAdapter extends BaseQuickAdapter<BalanceLogResponse.ItemsBean, BaseViewHolder> implements LoadMoreModule {
    public PaymentsAdapter() {
        super(R.layout.item_payments);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BalanceLogResponse.ItemsBean balanceLogResponse) {

    }
}
