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
 * type 状态 -1:不展示(有预付款项未付不显示给用户) )
 *  1:正在招
 *  2:已招满/待开工(所有用户已确认)
 *  3:工期中
 *  4:待结算
 *  5:待评价
 *  6:已完成
 *  7:已取消
 *  8:已过期
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
        switch (item.getItemType()) {
            case 1:
                ItemGetinfoApplyWorkersBinding dataBinding0 = (ItemGetinfoApplyWorkersBinding) helper.getDataBinding();
                dataBinding0.setItem(item);
                break;
        }
    }
}
