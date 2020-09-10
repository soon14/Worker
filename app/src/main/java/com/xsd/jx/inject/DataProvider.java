package com.xsd.jx.inject;


import com.xsd.jx.impl.AdvertImpl;
import com.xsd.jx.impl.SiteImpl;
import com.xsd.jx.impl.WorkImpl;

import javax.inject.Inject;

/**
 * Date: 2019/11/25
 * author: SmallCake
 * 数据提供者
 */
public class DataProvider {
    @Inject
    public DataProvider(){}
    @Inject
    public AdvertImpl ad;
    @Inject
    public SiteImpl site;
    @Inject
    public WorkImpl work;

}
