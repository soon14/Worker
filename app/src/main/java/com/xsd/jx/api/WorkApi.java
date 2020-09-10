package com.xsd.jx.api;

import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.bean.WorkTypeResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Date: 2020/9/8
 * author: SmallCake
 * 工种相关接口
 *
 * 招工信息
 */
public interface WorkApi {
    //工种列表
    @GET("work-type/list")
    Observable<BaseResponse<List<WorkTypeResponse>>> workTypeList();
    //提交选择的工种
    @GET("work-type/submit-choice")
    Observable<BaseResponse<MessageBean>> workTypeSubmitChoice(@Query("ids")String ids);
    //登录用户推荐工作
    @GET("work/recommend")
    Observable<BaseResponse<MessageBean>> recommend();
    //招工信息列表 type integer招工类型 0:不限 1:短期工 2：长期工
    @GET("work/list")
    Observable<BaseResponse<WorkListResponse>> list(@Query("page")Integer page,@Query("type")Integer type);
    //查工价
    @GET("work/price")
    Observable<BaseResponse<List<JobSearchBean>>> price(@Query("areaId")Integer areaId, @Query("keywords")String keywords);


}
