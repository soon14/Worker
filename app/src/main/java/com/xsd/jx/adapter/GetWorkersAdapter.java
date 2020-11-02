package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.databinding.ItemGetWorkers1Binding;
import com.xsd.jx.databinding.ItemGetWorkers2Binding;
import com.xsd.jx.databinding.ItemGetWorkers3Binding;
import com.xsd.jx.databinding.ItemGetWorkers4Binding;
import com.xsd.jx.databinding.ItemGetWorkers5Binding;
import com.xsd.jx.databinding.ItemGetWorkers6Binding;
import com.xsd.jx.databinding.ItemGetWorkers7Binding;

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
public class GetWorkersAdapter extends BaseMultiItemQuickAdapter<MyGetWorkersResponse.ItemsBean, BaseDataBindingHolder> implements LoadMoreModule {
    public GetWorkersAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(-1, R.layout.item_null);
        addItemType(1, R.layout.item_get_workers_1);
        addItemType(2, R.layout.item_get_workers_2);
        addItemType(3, R.layout.item_get_workers_3);
        addItemType(4, R.layout.item_get_workers_4);
        addItemType(5, R.layout.item_get_workers_5);
        addItemType(6, R.layout.item_get_workers_6);
        addItemType(7, R.layout.item_get_workers_7);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, MyGetWorkersResponse.ItemsBean item) {
        // 根据返回的 type 分别设置数据
        switch (item.getItemType()) {
            case 1:
                ((ItemGetWorkers1Binding) helper.getDataBinding()).setItem(item);
                break;
            case 2:
                ((ItemGetWorkers2Binding) helper.getDataBinding()).setItem(item);
                break;
            case 3:
                ((ItemGetWorkers3Binding) helper.getDataBinding()).setItem(item);
                break;
            case 4:
                ((ItemGetWorkers4Binding) helper.getDataBinding()).setItem(item);
                break;
            case 5:
                ((ItemGetWorkers5Binding) helper.getDataBinding()).setItem(item);
                break;
            case 6:
                ((ItemGetWorkers6Binding) helper.getDataBinding()).setItem(item);
                break;
            case 7:
                ((ItemGetWorkers7Binding) helper.getDataBinding()).setItem(item);
                break;


        }
    }
}
