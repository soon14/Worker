package com.xsd.jx.utils;

import android.text.TextUtils;

import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.UserInfo;
import com.xsd.utils.SPUtils;


/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class UserUtils {
    //登录成功后保存用户信息
    public static void saveLoginUser(LoginUserResponse loginUserResponse) {
        SPUtils.saveObject("user", loginUserResponse.getUser());
        SPUtils.put("token", loginUserResponse.getToken());
        SPUtils.put("is_first", loginUserResponse.isIs_first());
    }

    public static void clearLoginUser() {
        SPUtils.remove("user");
        SPUtils.remove("token");
        SPUtils.remove("is_first");
    }

    public static UserInfo getUser() {
        return SPUtils.readObject("user");
    }
    public static void saveUser(UserInfo user) {
         SPUtils.saveObject("user",user);
    }

    public static String getToken() {
        return (String) SPUtils.get("token", "");
    }

    public static boolean isFirst() {
        return (boolean) SPUtils.get("is_first", false);
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    public static boolean isChooseWork() {
        try {
            return getUser().isChooseWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isCertification() {
        try {
            return getUser().getIsCertification()==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
