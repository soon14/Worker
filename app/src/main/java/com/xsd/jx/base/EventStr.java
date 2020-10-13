package com.xsd.jx.base;

/**
 * Date: 2020/9/10
 * author: SmallCake
 */
public interface EventStr {
    String UPDATE_INVITE_LIST = "update_invite_list";
    String UPDATE_GET_WORKERS = "update_get_workers";
    String UPDATE_MY_GET_WORKERS = "update_my_get_workers";
    String UPDATE_USER_INFO = "update_user_info";
    String UPDATE_ORDER_LIST = "update_order_list";
    String UPDATE_COMMENT_LIST = "update_comment_list";//刷新企业端待评价列表
    String LOGIN_OUT = "login_out";
    String GO_LOGIN = "go_login";
    String GO_AUTH = "go_auth";//去实名认证
    String LOGIN_SUCCESS = "login_success";
    String SHOW_PUSH_JOB = "show_push_job";
    String CLOSE_GET_WORKERSINFO_ACTIVITY = "close_get_workersinfo_activity";
}
