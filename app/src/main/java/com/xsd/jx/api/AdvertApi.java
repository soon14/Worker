package com.xsd.jx.api;


import com.xsd.jx.bean.AdBean;
import com.xsd.jx.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

import static com.xsd.jx.base.Contants.AD_START_AD;

/**
 * Date: 2019/11/25
 * author: SmallCake
 * 广告相关
 * Retrofit使用说明 ：https://square.github.io/retrofit
 * GET:
 *  @GET("group/{id}/users")
 * Call<List < User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
 *
 *  @GET("group/{id}/users")
 * Call<List < User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);
 *  POST:
 *  @POST("users/new")
 * Call<User> createUser(@Body User user);
 *
 * @FormUrlEncoded
 * @POST("user/edit")
 * Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
 *
 */
public interface AdvertApi {

    //广告相关接口
    @GET(AD_START_AD)
    Observable<BaseResponse<AdBean>> startAd();
}
