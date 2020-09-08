package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.databinding.ItemJobSearchBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/18
 * author: SmallCake
 */
public class JobSearchAdapter extends BaseQuickAdapter<JobSearchBean, BaseDataBindingHolder<ItemJobSearchBinding>> {
    public JobSearchAdapter() {
        super(R.layout.item_job_search);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemJobSearchBinding> holder, JobSearchBean searchJobBean) {
        ItemJobSearchBinding dataBinding = holder.getDataBinding();

    }
}
