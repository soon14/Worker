package com.xsd.jx.inject;


import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Date: 2019/11/25
 * author: SmallCake
 * 注入器
 */
@Singleton
@Component(modules = NetWorkMoudle.class)
public interface CommonComponent {
    void inject(BaseFragment baseFragment);
    void inject(BaseActivity baseActivity);
}
