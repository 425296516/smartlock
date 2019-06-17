package com.anlida.smartlock.model;

import com.anlida.smartlock.config.UserConfig;
import com.anlida.smartlock.model.req.BindPost;
import com.anlida.smartlock.model.req.LoginPost;
import com.anlida.smartlock.model.req.GetCarListPost;
import com.anlida.smartlock.model.req.ManagerLoginPost;
import com.anlida.smartlock.model.req.UploadImagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DriverInfo;
import com.anlida.smartlock.model.resp.UserCarListResponse;
import com.anlida.smartlock.model.resp.UserInfo;
import com.anlida.smartlock.network.api.ApiService;

import io.reactivex.Flowable;

public class UserDataSourceImpl implements UserDataSource {


    @Override
    public Flowable<UserInfo> loginByDriver(LoginPost post) {
        return ApiService.createUserService().loginByDriver(post)
                .flatMap(result -> Flowable.just(result));
    }

    @Override
    public Flowable<UserInfo> loginByManager(ManagerLoginPost post) {
        return ApiService.createUserService().loginByManager(post)
                .flatMap(result -> Flowable.just(result.getData().getUserInfo()));
    }

    @Override
    public Flowable<HttpResult> sendAuthCode(String mobile, String setUp) {
        return ApiService.createUserService().sendAuthCode(mobile, setUp);
    }

    @Override
    public Flowable<UserInfo> getUserInfo() {
        return ApiService.createUserService().getUserInfo()
                .flatMap(result -> Flowable.just(result.getData().getUserInfo()));
    }

    @Override
    public Flowable<HttpResult> uploadHeadImage(UploadImagePost post) {
        return ApiService.createUserService().uploadHeadImage(post);
    }

    @Override
    public UserInfo getUserInfoFromCache() {
        return UserConfig.getUserInfo();
    }

    @Override
    public void setUserInfoToCache(UserInfo userInfo) {
        UserConfig.setUserInfo(userInfo);
    }

    @Override
    public Flowable<CarInfo> getCarInfo() {
        return ApiService.createUserService().getCarInfoForDriver()
                .flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<HttpResult> bindCar(BindPost post) {
        return ApiService.createUserService().bindCar(post);
    }

    @Override
    public Flowable<UserCarListResponse> getCarListForManager(GetCarListPost post) {
        return ApiService.createUserService().getCarListForManager(post)
                .flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<DriverInfo> getDriverDetail(String vin) {
        return ApiService.createUserService().getDriverDetail(vin)
                .flatMap(result -> Flowable.just(result.getData()));
    }
}
