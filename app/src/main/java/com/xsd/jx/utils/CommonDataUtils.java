package com.xsd.jx.utils;

import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.PlatPhoneResponse;
import com.xsd.utils.SPUtils;

/**
 * Date: 2020/10/13
 * author: SmallCake
 * 公共数据获取类
 */
public class CommonDataUtils {
    //获取公共的平台电话号码
    public static void getPhone(BaseActivity activity){
        activity.getDataProvider().site.platPhone()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PlatPhoneResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PlatPhoneResponse> baseResponse) {
                        String platPhone = baseResponse.getData().getPlatPhone();
                        SPUtils.put("platPhone",platPhone);
                    }
                });
    }
}
