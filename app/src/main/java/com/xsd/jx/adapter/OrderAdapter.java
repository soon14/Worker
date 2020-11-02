package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.databinding.ItemOrder1Binding;
import com.xsd.jx.databinding.ItemOrder2Binding;
import com.xsd.jx.databinding.ItemOrder3Binding;
import com.xsd.jx.databinding.ItemOrder4Binding;
import com.xsd.jx.databinding.ItemOrder5Binding;
import com.xsd.jx.databinding.ItemOrder6Binding;
import com.xsd.jx.databinding.ItemOrder7Binding;
import com.xsd.jx.databinding.ItemOrder8Binding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/26
 * author: SmallCake
 * 接口type 类型 0:全部
 * 1:报名中
 * 2:待开工
 * 3:已招满/被拒绝
 * 4:已取消
 * 5:工期中
 * 6:待结算
 * 7:待评价
 * 8:已完成
 */
public class OrderAdapter extends BaseMultiItemQuickAdapter<OrderBean, BaseDataBindingHolder> implements LoadMoreModule {
    public OrderAdapter() {
        super();
        addItemType(1, R.layout.item_order_1);
        addItemType(2, R.layout.item_order_2);
        addItemType(3, R.layout.item_order_3);
        addItemType(4, R.layout.item_order_4);//工价灰色且无操作按钮
        addItemType(5, R.layout.item_order_5);
        addItemType(6, R.layout.item_order_6);
        addItemType(7, R.layout.item_order_7);
        addItemType(8, R.layout.item_order_8);//工价灰色且无操作按钮
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, OrderBean orderBean) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case 1:
                ((ItemOrder1Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 2:
                ((ItemOrder2Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 3:
                ((ItemOrder3Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 4:
                ((ItemOrder4Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 5:
                ((ItemOrder5Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 6:
                ((ItemOrder6Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 7:
                ((ItemOrder7Binding) helper.getDataBinding()).setItem(orderBean);
                break;
            case 8:
                ((ItemOrder8Binding) helper.getDataBinding()).setItem(orderBean);
                break;


        }
    }
}
