package com.anlida.smartlock.network.api;


import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.SearchCarListPost;
import com.anlida.smartlock.model.req.SearchCarPost;
import com.anlida.smartlock.model.req.SearchListPagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DataBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CarService {
    @POST("location/search")
    Flowable<HttpResult<List<CarInfo>>> searchCar(@Body SearchCarPost post);
    /*locateFragment 获取列表*/
    @POST("location/detail/manager")
    Flowable<HttpResult<List<CarInfo>>> searchCarList(@Body SearchCarListPost post);
    /*司机locateFragment 获取绑定车辆*/
    @POST("location/detail/driver")
    Flowable<HttpResult<List<CarInfo>>> searchCarDriver();
    /*管理员carListActivity页面获取车辆列表*/
    @POST("location/detailList")
    Flowable<HttpResult<DataBean>> searchCatListAllPage(@Body SearchListPagePost post);
}
