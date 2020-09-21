package com.xsd.jx.inject;


import com.xsd.jx.impl.OrderImpl;
import com.xsd.jx.impl.ServerImpl;
import com.xsd.jx.impl.SiteImpl;
import com.xsd.jx.impl.UserImpl;
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
    public SiteImpl site;
    @Inject
    public WorkImpl work;
    @Inject
    public OrderImpl order;
    @Inject
    public UserImpl user;
    @Inject
    public ServerImpl server;

}
