package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ItemMygetworkersCancelBinding;
import com.xsd.jx.databinding.ItemMygetworkersCompletionBinding;
import com.xsd.jx.databinding.ItemMygetworkersFullBinding;
import com.xsd.jx.databinding.ItemMygetworkersGetingBinding;
import com.xsd.jx.databinding.ItemMygetworkersWaitcommentBinding;
import com.xsd.jx.databinding.ItemMygetworkersWaitpayBinding;
import com.xsd.jx.databinding.ItemMygetworkersWorkingBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/26
 * author: SmallCake
 * 正在招
 * 已招满
 * 工期内
 * 待结算
 * 待评价
 * 全部订单：除了上面2个还有：已完成，已取消
 */
public class MyWorkersAdapter extends BaseMultiItemQuickAdapter<WorkerResponse, BaseDataBindingHolder> {
    public MyWorkersAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(0, R.layout.item_mygetworkers_geting);
        addItemType(1, R.layout.item_mygetworkers_full);
        addItemType(2, R.layout.item_mygetworkers_working);
        addItemType(3, R.layout.item_mygetworkers_waitpay);
        addItemType(4, R.layout.item_mygetworkers_waitcomment);
        addItemType(5, R.layout.item_mygetworkers_completion);
        addItemType(6, R.layout.item_mygetworkers_cancel);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, WorkerResponse item) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case 0:
                ItemMygetworkersGetingBinding dataBinding0 = (ItemMygetworkersGetingBinding) helper.getDataBinding();
                dataBinding0.setItem(item);
                break;
            case 1:
                ItemMygetworkersFullBinding dataBinding1 = (ItemMygetworkersFullBinding) helper.getDataBinding();
                dataBinding1.setItem(item);
                break;
            case 2:
                ItemMygetworkersWorkingBinding dataBinding2 = (ItemMygetworkersWorkingBinding) helper.getDataBinding();
                dataBinding2.setItem(item);
                break;
            case 3:
                ItemMygetworkersWaitpayBinding dataBinding3 = (ItemMygetworkersWaitpayBinding) helper.getDataBinding();
                dataBinding3.setItem(item);
                break;
            case 4:
                ItemMygetworkersWaitcommentBinding dataBinding4 = (ItemMygetworkersWaitcommentBinding) helper.getDataBinding();
                dataBinding4.setItem(item);
                break;
            case 5:
                ItemMygetworkersCompletionBinding dataBinding5 = (ItemMygetworkersCompletionBinding) helper.getDataBinding();
                dataBinding5.setItem(item);
                break;
            case 6:
                ItemMygetworkersCancelBinding dataBinding6 = (ItemMygetworkersCancelBinding) helper.getDataBinding();
                dataBinding6.setItem(item);
                break;

        }
    }
}
