package com.xsd.jx.base;

import com.xsd.jx.utils.UserUtils;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public interface Contants {
    String BASE_URL = "https://s.xhjxapp.com/api/v1/";
    String UM_APP_KEY = "5f719522906ad811171745e3";
    String SHARE_URL = UserUtils.getUserInfo().getShareUrl();
    String YSZC_URL = "https://s.xhjxapp.com/html/yszc";
    String YHXY_URL = "https://s.xhjxapp.com/html/yhxy";
}
