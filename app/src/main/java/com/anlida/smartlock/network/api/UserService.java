package com.anlida.smartlock.network.api;


import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.BindPost;
import com.anlida.smartlock.model.req.LoginPost;
import com.anlida.smartlock.model.req.GetCarListPost;
import com.anlida.smartlock.model.req.ManagerLoginPost;
import com.anlida.smartlock.model.req.UploadImagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DriverInfo;
import com.anlida.smartlock.model.resp.UserCarListResponse;
import com.anlida.smartlock.model.resp.UserInfo;
import com.anlida.smartlock.model.resp.UserInfoResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    //司机登陆
    @POST("sys/AndroidLogin")
    Flowable<UserInfo> loginByDriver(@Body LoginPost post);

    //管理员登陆
    @POST("login/manager")
    Flowable<HttpResult<UserInfoResponse>> loginByManager(@Body ManagerLoginPost post);

    //获取验证码
    @GET("auth/code")
    Flowable<HttpResult> sendAuthCode(@Query("mobile") String mobile, @Query("setUp") String setUp);

    //获取个人信息
    @GET("user/details")
    Flowable<HttpResult<UserInfoResponse>> getUserInfo();

    //上传头像
    @POST("user/upload-pic")
    Flowable<HttpResult> uploadHeadImage(@Body UploadImagePost post);

    //绑定解绑车辆
    @POST("user-vehicle/binding")
    Flowable<HttpResult> bindCar(@Body BindPost post);

    //管理员车辆列表
    @POST("user-vehicle/home/manager")
    Flowable<HttpResult<UserCarListResponse>> getCarListForManager(@Body GetCarListPost post);

    //司机首页信息
    @GET("user-vehicle/home/driver")
    Flowable<HttpResult<CarInfo>> getCarInfoForDriver();

    //司机详情
    @GET("user-vehicle/home/manager/driverDetail")
    Flowable<HttpResult<DriverInfo>> getDriverDetail(@Query("vin") String vin);
}
