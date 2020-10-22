package com.xsd.jx.api;

import com.xsd.jx.bean.BalanceLogResponse;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.DivisionBean;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.HelpRegResponse;
import com.xsd.jx.bean.IsInWorkResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MessageResponse;
import com.xsd.jx.bean.RecommendResponse;
import com.xsd.jx.bean.StsResponse;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.bean.WithdrawInfoResponse;

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
    //用户资金的变动记录列表
    @GET("user/balance-log")
    Observable<BaseResponse<BalanceLogResponse>> balanceLog(@Query("page")Integer page);
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

    //上传头像，改用阿里云上传头像
    @Deprecated
    @Multipart
    @POST("user/upload-avatar")
    Observable<BaseResponse<MessageBean>> uploadAvatar(@Part MultipartBody.Part body);

    //用户提现资料信息
    @GET("user/withdraw-info")
    Observable<BaseResponse<WithdrawInfoResponse>> withdrawInfo();

    /**
     * 提现申请
     * @param amount      提现金额
     * @param accountType 支付类型 1:支付宝 2:银行卡 3:周边事业部 4:微信
     * @param account     账号
     * @param name        收款人姓名
     * @param divisionId  事业部ID
     * @param bankName    银行名称
     */
    @FormUrlEncoded
    @POST("user/withdraw")
    Observable<BaseResponse<MessageBean>> withdraw(
             @Field("amount")Integer amount
            ,@Field("accountType")Integer accountType
            ,@Field("account")String account
            ,@Field("name")String name
            ,@Field("divisionId")Integer divisionId
            ,@Field("bankName")String bankName
    );


    //编辑用户资料
    @Multipart
    @POST("user/profile")
    Observable<BaseResponse<MessageBean>> profile(@PartMap Map<String, RequestBody> map);

    //我推荐的工友
    @GET("user/recommend")
    Observable<BaseResponse<RecommendResponse>> recommend(@Query("page") Integer page);

    //邀请奖励记录列表
    @GET("user/reward-log")
    Observable<BaseResponse<BalanceLogResponse>> rewardLog(@Query("page") Integer page);

    //用户消息记录列表 ,type:类别默认1 1:系统消息 2：订单消息
    @GET("user/message")
    Observable<BaseResponse<MessageResponse>> message(@Query("type") Integer type,@Query("page") Integer page);
    //我推荐的工友
    @GET("user/division")
    Observable<BaseResponse<List<DivisionBean>>> division();

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

    //实名认证
    @FormUrlEncoded
    @POST("user/certification")
    Observable<BaseResponse<MessageBean>> certification(@Field("avatar")String avatar,@Field("name")String name,@Field("idCard")String idCard,@Field("nation")String nation,@Field("workYears")String workYears);

    /**
     * 城市合伙人意向提交
     * @param name    姓名
     * @param mobile  联系手机号
     * @param idType  身份 1:建筑企业 2:劳务公司 3:个人
     * @param purpose 合作意向 1:项目入股 2:资源合作 3:委托招工
     * @return MessageBean
     */
    @FormUrlEncoded
    @POST("user/city-partner")
    Observable<BaseResponse<MessageBean>> cityPartner(@Field("name")String name,@Field("mobile")String mobile, @Field("idType")Integer idType,@Field("purpose")Integer purpose);
    //用户反馈
    @FormUrlEncoded
    @POST("user/feedback")
    Observable<BaseResponse<MessageBean>> feedback(@Field("content")String content,@Field("contentUrl")String contentUrl);

    //打开APP检查当前用户当日是否有上工
    @GET("user/is-in-work")
    Observable<BaseResponse<IsInWorkResponse>> isInWork();

    //阿里sts
    @GET("user/ali-sts")
    Observable<BaseResponse<StsResponse>> aliSts();



}
