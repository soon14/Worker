package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.OrderBean;
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
 * 接口type 类型 0:全部
 * 1:未确认
 * 2:待开工
 * 3:已招满（被拒绝）
 * 4:已取消
 * 5:进行中
 * 6:待结算
 * 7:待评价
 * 8:已完成
 */
public class OrderAdapter extends BaseMultiItemQuickAdapter<OrderBean, BaseDataBindingHolder> implements LoadMoreModule {
    public OrderAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(1, R.layout.item_applying);
        addItemType(2, R.layout.item_wait_work);
        addItemType(5, R.layout.item_working);
        addItemType(6, R.layout.item_wait_pay);
        addItemType(7, R.layout.item_wait_comment);
        addItemType(8, R.layout.item_order_completion);
        addItemType(4, R.layout.item_order_cancel);
        addItemType(3, R.layout.item_worker_full);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, OrderBean orderBean) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case 1:
                ItemApplyingBinding dataBinding0 = (ItemApplyingBinding) helper.getDataBinding();
                dataBinding0.setItem(orderBean);
                break;
            case 2:
                ItemWaitWorkBinding dataBinding1 = (ItemWaitWorkBinding) helper.getDataBinding();
                dataBinding1.setItem(orderBean);
                break;
            case 5:
                ItemWorkingBinding dataBinding2 = (ItemWorkingBinding) helper.getDataBinding();
                dataBinding2.setItem(orderBean);
                break;
            case 6:
                ItemWaitPayBinding dataBinding3 = (ItemWaitPayBinding) helper.getDataBinding();
                dataBinding3.setItem(orderBean);
                break;
            case 7:
                ItemWaitCommentBinding dataBinding4 = (ItemWaitCommentBinding) helper.getDataBinding();
                dataBinding4.setItem(orderBean);
                break;
            case 8:
                ItemOrderCompletionBinding dataBinding5 = (ItemOrderCompletionBinding) helper.getDataBinding();
                dataBinding5.setItem(orderBean);
                break;
            case 4:
                ItemOrderCancelBinding dataBinding6 = (ItemOrderCancelBinding) helper.getDataBinding();
                dataBinding6.setItem(orderBean);
                break;
            case 3:
                ItemWorkerFullBinding dataBinding7 = (ItemWorkerFullBinding) helper.getDataBinding();
                dataBinding7.setItem(orderBean);
                break;

        }
    }
}
