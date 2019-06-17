package com.anlida.smartlock.model;

import com.anlida.smartlock.model.req.BindPost;
import com.anlida.smartlock.model.req.LoginPost;
import com.anlida.smartlock.model.req.GetCarListPost;
import com.anlida.smartlock.model.req.ManagerLoginPost;
import com.anlida.smartlock.model.req.UploadImagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DriverInfo;
import com.anlida.smartlock.model.resp.UserCarListResponse;
import com.anlida.smartlock.model.resp.UserInfo;

import io.reactivex.Flowable;


public interface UserDataSource {
    //司机登陆
    Flowable<UserInfo> loginByDriver(LoginPost post);

    //管理员登陆
    Flowable<UserInfo> loginByManager(ManagerLoginPost post);

    //获取验证码
    Flowable<HttpResult> sendAuthCode(String mobile, String setUp);

    //获取个人信息
    Flowable<UserInfo> getUserInfo();

    //上传头像
    Flowable<HttpResult> uploadHeadImage(UploadImagePost post);

    //从缓存中获取用户信息
    UserInfo getUserInfoFromCache();

    //将用户信息写入缓存
    void setUserInfoToCache(UserInfo userInfo);

    Flowable<CarInfo> getCarInfo();

    Flowable<HttpResult> bindCar(BindPost post);

    Flowable<UserCarListResponse> getCarListForManager(GetCarListPost post);

    Flowable<DriverInfo> getDriverDetail(String vin);
}
