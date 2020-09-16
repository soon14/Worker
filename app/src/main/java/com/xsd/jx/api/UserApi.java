package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.HelpRegResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.RecommendResponse;
import com.xsd.jx.bean.UserInfoResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Observable<BaseResponse<ExperienceResponse>> experience(@Query("page") Integer page);
    //用户收藏的有效工作列表
    @GET("user/fav-works")
    Observable<BaseResponse<List<JobBean>>> favWorks(@Query("page") Integer page);

    //帮工友注册记录
    @GET("user/help-reg-record")
    Observable<BaseResponse<HelpRegResponse>> helpRegRecord(@Query("page") Integer page);

    //上传头像
    @Multipart
    @POST("user/upload-avatar")
    Observable<BaseResponse<MessageBean>> uploadAvatar(@Part MultipartBody.Part body);

    //编辑用户资料
    @Multipart
    @POST("user/profile")
    Observable<BaseResponse<MessageBean>> profile(@PartMap Map<String, RequestBody> map);

    //我推荐的工友
    @GET("user/recommend")
    Observable<BaseResponse<RecommendResponse>> recommend(@Query("page") Integer page);

    /**
     * 帮工友注册账号
     *
     * @param mobile    工友手机号
     * @param code      工友收到的验证码
     * @param name      工友姓名
     * @param idCard    工友身份证号
     * @param wtIds     工友的工种ID，用英文逗号分隔 1,2,3,4
     * @param nation    民族
     * @param workYears 工作年限
     * @return MessageBean
     */
    @FormUrlEncoded
    @POST("user/help-reg")
    Observable<BaseResponse<MessageBean>> helpReg(@Field("mobile") String mobile, @Field("code") String code, @Field("name") String name, @Field("idCard") String idCard, @Field("wtIds") String wtIds, @Field("nation") String nation, @Field("workYears") String workYears);

}
