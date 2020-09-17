package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.MessageResponse;
import com.xsd.jx.databinding.ItemMessageBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class MessageAdapter extends BaseQuickAdapter<MessageResponse.ItemsBean, BaseDataBindingHolder<ItemMessageBinding>> implements LoadMoreModule {

    public MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemMessageBinding> baseViewHolder, MessageResponse.ItemsBean messageResponse) {
        baseViewHolder.getDataBinding().setItem(messageResponse);
    }
}
