package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.HelpRegResponse;
import com.xsd.jx.databinding.ItemHelpRegistBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/24
 * author: SmallCake
 */
public class HelpRegistAdapter extends BaseQuickAdapter<HelpRegResponse.ItemsBean, BaseDataBindingHolder<ItemHelpRegistBinding>> implements LoadMoreModule {
    public HelpRegistAdapter() {
        super(R.layout.item_help_regist);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemHelpRegistBinding> holder, HelpRegResponse.ItemsBean item) {
        holder.getDataBinding().setItem(item);
    }
}
