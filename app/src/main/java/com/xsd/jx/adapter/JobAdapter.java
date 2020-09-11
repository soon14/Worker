package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ItemJobBinding;
import com.xsd.utils.L;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/18
 * author: SmallCake
 */
public class JobAdapter extends BaseQuickAdapter<JobBean, BaseDataBindingHolder<ItemJobBinding>> implements LoadMoreModule {

    public JobAdapter() {
        super(R.layout.item_job);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemJobBinding> helper, JobBean jobBean) {
        ItemJobBinding dataBinding = helper.getDataBinding();
        L.e(helper.getLayoutPosition()+"=="+jobBean.isIsJoin());
        dataBinding.setItem(jobBean);
    }
    public void refreshData(int position){
        notifyItemChanged(position);
    }




}
