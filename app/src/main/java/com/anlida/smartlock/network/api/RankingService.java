package com.anlida.smartlock.network.api;

import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqRankingListBean;
import com.anlida.smartlock.model.resp.RespBusGroup;
import com.anlida.smartlock.model.resp.RespDriverRanking;
import com.anlida.smartlock.model.resp.RespProvinCity;
import com.anlida.smartlock.model.resp.RespRankingListBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RankingService {

    //获取司机自己的排行
    @GET("ranking/driver")
    Flowable<HttpResult<RespDriverRanking>> getDriverRanking(@Query("type") int type);

    //获取排行榜
    @POST("ranking/list")
    Flowable<HttpResult<RespRankingListBean>> getRankingList(@Body ReqRankingListBean reqRankingListBean);

    //超级管理员获取省市信息
    @GET("ranking/province-city")
    Flowable<HttpResult<List<RespProvinCity>>> getProvinceCity();

    //6.4 超级管理员获取公交集团列表
    @GET("ranking/busgroup")
    Flowable<HttpResult<List<RespBusGroup>>> getBusGroup();


}

