package com.xsd.jx.utils;

import android.text.TextUtils;

import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.utils.SPUtils;

import java.util.List;


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
        SPUtils.remove("workTypes");
    }

    public static UserInfo getUserInfo() {
        return SPUtils.readObject("user");
    }
    public static List<WorkTypeBean> getWorkTypes() {
        return SPUtils.readObject("workTypes");
    }
    public static void saveUser(UserInfoResponse user) {
         SPUtils.saveObject("user",user.getInfo());
         SPUtils.saveObject("workTypes",user.getWorkTypes());
    }
    public static void saveUserInfo(UserInfo user) {
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
            return getUserInfo().isChooseWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否已经实名认证
     */
    public static boolean isCertification() {
        try {
            return getUserInfo().getIsCertification()==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
