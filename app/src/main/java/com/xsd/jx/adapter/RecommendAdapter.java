package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.RecommendResponse;
import com.xsd.jx.databinding.ItemRecommendBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendResponse.ItemsBean, BaseDataBindingHolder<ItemRecommendBinding>> implements LoadMoreModule {
    public RecommendAdapter() {
        super(R.layout.item_recommend);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemRecommendBinding> holder, RecommendResponse.ItemsBean item) {
        holder.getDataBinding().setItem(item);
    }
}
