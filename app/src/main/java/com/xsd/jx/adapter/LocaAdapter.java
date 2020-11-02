package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.DivisionBean;
import com.xsd.jx.databinding.ItemDivisionBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/27
 * author: SmallCake
 * 本地事业部适配器
 */
public class LocaAdapter extends BaseQuickAdapter<DivisionBean, BaseDataBindingHolder<ItemDivisionBinding>> {
    public LocaAdapter() {
        super(R.layout.item_division);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemDivisionBinding> holder, DivisionBean item) {
            holder.getDataBinding().setItem(item);
    }
}
