package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.MyGetWorkersResponse;
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
 * 我的招工适配器
 * status
 * 状态 -1:不展示(有预付款项未付不显示给用户) )
 * 1:正在招
 * 2:已招满/待开工(所有用户已确认)
 * 3:工期中
 * 4:待结算
 * 5:待评价
 * 6:已完成
 * 7.已取消
 */
public class MyWorkersAdapter extends BaseMultiItemQuickAdapter<MyGetWorkersResponse.ItemsBean, BaseDataBindingHolder> implements LoadMoreModule {
    public MyWorkersAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(-1, R.layout.item_null);
        addItemType(1, R.layout.item_mygetworkers_geting);
        addItemType(2, R.layout.item_mygetworkers_full);
        addItemType(3, R.layout.item_mygetworkers_working);
        addItemType(4, R.layout.item_mygetworkers_waitpay);
        addItemType(5, R.layout.item_mygetworkers_waitcomment);//待评价
        addItemType(6, R.layout.item_mygetworkers_completion);
        addItemType(7, R.layout.item_mygetworkers_cancel);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, MyGetWorkersResponse.ItemsBean item) {
        // 根据返回的 type 分别设置数据
        switch (item.getItemType()) {
            case 1:
                ItemMygetworkersGetingBinding dataBinding0 = (ItemMygetworkersGetingBinding) helper.getDataBinding();
                dataBinding0.setItem(item);
                break;
            case 2:
                ItemMygetworkersFullBinding dataBinding1 = (ItemMygetworkersFullBinding) helper.getDataBinding();
                dataBinding1.setItem(item);
                break;
            case 3:
                ItemMygetworkersWorkingBinding dataBinding2 = (ItemMygetworkersWorkingBinding) helper.getDataBinding();
                dataBinding2.setItem(item);
                break;
            case 4:
                ItemMygetworkersWaitpayBinding dataBinding3 = (ItemMygetworkersWaitpayBinding) helper.getDataBinding();
                dataBinding3.setItem(item);
                break;
            case 5:
                ItemMygetworkersWaitcommentBinding dataBinding4 = (ItemMygetworkersWaitcommentBinding) helper.getDataBinding();
                dataBinding4.setItem(item);
                break;
            case 6:
                ItemMygetworkersCompletionBinding dataBinding5 = (ItemMygetworkersCompletionBinding) helper.getDataBinding();
                dataBinding5.setItem(item);
                break;
            case 7:
                ItemMygetworkersCancelBinding dataBinding6 = (ItemMygetworkersCancelBinding) helper.getDataBinding();
                dataBinding6.setItem(item);
                break;

        }
    }
}
