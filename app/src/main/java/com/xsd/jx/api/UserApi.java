package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfoResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public interface UserApi {
    //获取用户资料
    @GET("user/info")
    Observable<BaseResponse<UserInfoResponse>> info();
    //用户工作经历
    @GET("user/experience")
    Observable<BaseResponse<ExperienceResponse>> experience(@Query("page")Integer page);
    //上传头像
    @Multipart
    @POST("user/upload-avatar")
    Observable<BaseResponse<MessageBean>> uploadAvatar(@Part MultipartBody.Part body);

    //编辑用户资料
    @Multipart
    @POST("user/profile")
    Observable<BaseResponse<MessageBean>> profile(@PartMap Map<String, RequestBody> map);
}
