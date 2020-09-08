package com.xsd.jx.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.AdBean;
import com.xsd.jx.databinding.ItemAdBinding;

/**
 * Date: 2020/1/13
 * author: SmallCake
 */
public class AdBeanAdapter extends BaseQuickAdapter<AdBean, BaseDataBindingHolder<ItemAdBinding>> {
    public AdBeanAdapter() {
        super(R.layout.item_ad);
    }
    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemAdBinding> helper, AdBean item) {
        ItemAdBinding binding =  helper.getDataBinding();
        binding.setItem(item);
    }
}
