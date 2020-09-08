package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.MessageResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class MessageAdapter extends BaseQuickAdapter<MessageResponse, BaseViewHolder> {

    public MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageResponse messageResponse) {

    }
}
