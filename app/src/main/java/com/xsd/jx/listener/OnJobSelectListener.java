package com.xsd.jx.listener;

import com.xsd.jx.bean.JobListResponse;

/**
 * Date: 2020/9/22
 * author: SmallCake
 */
public interface OnJobSelectListener {
    void onSelect(JobListResponse.ItemsBean itemsBean);
}
