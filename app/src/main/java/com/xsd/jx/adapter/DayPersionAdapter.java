package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.DayPersionBean;
import com.xsd.jx.databinding.ItemDayPersionBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class DayPersionAdapter extends BaseQuickAdapter<DayPersionBean, BaseDataBindingHolder<ItemDayPersionBinding>> {
    public DayPersionAdapter( @Nullable List<DayPersionBean> data) {
        super(R.layout.item_day_persion, data);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemDayPersionBinding> holder, DayPersionBean item) {
        holder.getDataBinding().setItem(item);
    }
}
