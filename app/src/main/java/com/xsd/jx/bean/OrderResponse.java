package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class OrderResponse implements MultiItemEntity {

    public OrderResponse(int itemType) {
        this.itemType = itemType;
    }

    int itemType;
    @Override
    public int getItemType() {
        return itemType;
    }
}
