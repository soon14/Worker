package com.xsd.jx.listener;

import com.xsd.jx.bean.AddrBean;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public interface OnAddrListener  {
    void onAddrSelect(AddrBean city,AddrBean district);
}
