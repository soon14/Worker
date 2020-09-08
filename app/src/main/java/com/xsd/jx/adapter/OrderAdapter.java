package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.ItemApplyingBinding;
import com.xsd.jx.databinding.ItemOrderCancelBinding;
import com.xsd.jx.databinding.ItemOrderCompletionBinding;
import com.xsd.jx.databinding.ItemWaitCommentBinding;
import com.xsd.jx.databinding.ItemWaitPayBinding;
import com.xsd.jx.databinding.ItemWaitWorkBinding;
import com.xsd.jx.databinding.ItemWorkerFullBinding;
import com.xsd.jx.databinding.ItemWorkingBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/26
 * author: SmallCake
 * 报名中
 * 待开工
 * 工期中
 * 待结算
 * 待评价
 * 全部订单：除了上面5个还有：已完成，已取消，已招满
 */
public class OrderAdapter extends BaseMultiItemQuickAdapter<OrderResponse, BaseDataBindingHolder> {
    public OrderAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(0, R.layout.item_applying);
        addItemType(1, R.layout.item_wait_work);
        addItemType(2, R.layout.item_working);
        addItemType(3, R.layout.item_wait_pay);
        addItemType(4, R.layout.item_wait_comment);
        addItemType(5, R.layout.item_order_completion);
        addItemType(6, R.layout.item_order_cancel);
        addItemType(7, R.layout.item_worker_full);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, OrderResponse orderResponse) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case 0:
                ItemApplyingBinding dataBinding0 = (ItemApplyingBinding) helper.getDataBinding();
                dataBinding0.setItem(orderResponse);
                break;
            case 1:
                ItemWaitWorkBinding dataBinding1 = (ItemWaitWorkBinding) helper.getDataBinding();
                dataBinding1.setItem(orderResponse);
                break;
            case 2:
                ItemWorkingBinding dataBinding2 = (ItemWorkingBinding) helper.getDataBinding();
                dataBinding2.setItem(orderResponse);
                break;
            case 3:
                ItemWaitPayBinding dataBinding3 = (ItemWaitPayBinding) helper.getDataBinding();
                dataBinding3.setItem(orderResponse);
                break;
            case 4:
                ItemWaitCommentBinding dataBinding4 = (ItemWaitCommentBinding) helper.getDataBinding();
                dataBinding4.setItem(orderResponse);
                break;
            case 5:
                ItemOrderCompletionBinding dataBinding5 = (ItemOrderCompletionBinding) helper.getDataBinding();
                dataBinding5.setItem(orderResponse);
                break;
            case 6:
                ItemOrderCancelBinding dataBinding6 = (ItemOrderCancelBinding) helper.getDataBinding();
                dataBinding6.setItem(orderResponse);
                break;
            case 7:
                ItemWorkerFullBinding dataBinding7 = (ItemWorkerFullBinding) helper.getDataBinding();
                dataBinding7.setItem(orderResponse);
                break;

        }
    }
}
