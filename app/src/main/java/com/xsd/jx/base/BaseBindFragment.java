package com.xsd.jx.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Date: 2020/1/4
 * author: SmallCake
 * 1.配置了泛型，DataBind的方式来构建基类Fragment
 */
public abstract class BaseBindFragment<DB extends ViewDataBinding> extends BaseFragment {
    protected DB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        db = DataBindingUtil.bind(view);
        onBindView(view, container, savedInstanceState);
        return view;
    }
}
