package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.ItemGetinfoApplyWorkersBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/28
 * author: SmallCake
 * 招工详情页，工人信息列表适配器
 */
public class GetWorkersInfoAdapter extends BaseMultiItemQuickAdapter<WorkerBean, BaseDataBindingHolder> {

    public GetWorkersInfoAdapter() {
        super();
        // 绑定 layout 对应的 type
        addItemType(1, R.layout.item_getinfo_apply_workers);
        addItemType(2, R.layout.item_getinfo_full);
        addItemType(3, R.layout.item_getinfo_full);
        addItemType(4, R.layout.item_getinfo_waitpay);
        addItemType(5, R.layout.item_getinfo_waitcomment);

        addItemType(6, R.layout.item_order_completion);
        addItemType(7, R.layout.item_order_cancel);
        addItemType(8, R.layout.item_worker_full);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, WorkerBean item) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case 1:
                ItemGetinfoApplyWorkersBinding dataBinding0 = (ItemGetinfoApplyWorkersBinding) helper.getDataBinding();
                dataBinding0.setItem(item);
                break;
        }
    }
}
