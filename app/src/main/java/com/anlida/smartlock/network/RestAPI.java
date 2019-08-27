package com.anlida.smartlock.network;

import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.LoginPost;
import com.anlida.smartlock.model.req.ReqDealWarning;
import com.anlida.smartlock.model.req.ReqDeviceManager;
import com.anlida.smartlock.model.req.ReqDeviceUse;
import com.anlida.smartlock.model.req.ReqManagerInfo;
import com.anlida.smartlock.model.req.ReqPersonCenter;
import com.anlida.smartlock.model.req.ReqRepairInfo;
import com.anlida.smartlock.model.req.ReqSearchWarning;
import com.anlida.smartlock.model.req.ReqUnLockList;
import com.anlida.smartlock.model.req.ReqUpdatePhone;
import com.anlida.smartlock.model.req.ReqWarnRecord;
import com.anlida.smartlock.model.req.ReqWorkerInfo;
import com.anlida.smartlock.model.resp.RespDeviceManager;
import com.anlida.smartlock.model.resp.RespPersonCenter;
import com.anlida.smartlock.model.resp.RespRemoteToken;
import com.anlida.smartlock.model.resp.RespWarnLocation;
import com.anlida.smartlock.model.resp.RespWarnRecord;
import com.anlida.smartlock.model.resp.UserInfo;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by (张慈瑞).
 */

public interface RestAPI {

    //1.登录接口
    @POST("sys/AndroidLogin")
    Flowable<UserInfo> login(@Body LoginPost post);

    //2.提交工人信息的接口
    @POST("/userandcontruction/add")
    Flowable<HttpResult> addWorkerInfo(@Body ReqWorkerInfo reqWorkerInfo);

    //3.协同责任人信息提交
    @POST("/responsibleperson/add")
    Flowable<HttpResult> addManagerInfo(@Body ReqManagerInfo reqManagerInfo);

    @Multipart
    @POST("/idcardimg/front")
    Flowable<HttpResult> upLoadFrontImage(@Part("userId") RequestBody userId, @Part MultipartBody.Part file);

    @Multipart
    @POST("/idcardimg/behind")
    Flowable<HttpResult> upLoadBackImage(@Part("userId") RequestBody userId, @Part MultipartBody.Part file);

    //获取预警设备位置的接口
    @GET("/warninglog/getCoordinate")
    Flowable<RespWarnLocation> getWarningLocation();

    //获取预警记录接口
    @POST("/warninglog/page")
    Flowable<RespWarnRecord> getWarningRecord(@Body ReqWarnRecord reqWarnRecord);

    //搜索预警记录接口
    @POST("/warninglog/page")
    Flowable<RespWarnRecord> getSearchWarningRecord(@Body ReqSearchWarning reqSearchWarning);

    //查看申请解锁的列表
    @POST("/warninglog/page")
    Flowable<RespWarnRecord> getUnlockList(@Body ReqUnLockList reqUnLockList);

    //处理预警记录接口
    @POST("/warninglog/edit")
    Flowable<RespWarnRecord> dealWarningRecord(@Body ReqDealWarning reqDealWarning);

    //获取设备管理的接口
    @POST("/userandlock/page")
    Flowable<RespDeviceManager> getDeviceManager(@Body ReqDeviceManager reqDeviceManager);

    //搜索设备的接口
    @POST("/userandlock/pages")
    Flowable<RespDeviceManager> searchDeviceManager(@Body ReqDeviceManager reqDeviceManager);

    //设备上锁的接口
    @GET("/devices/op/commands")
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Flowable<HttpResult> deviceLocks( @Query("userName") String userName, @Query("command") String command,@Query("imei") String imei);

    //设备解锁的接口
    @GET("/devices/op/Androidjiesuo")
    Flowable<HttpResult> deviceunLock(@Query("command") String command,@Query("imei") String imei);

    //获取指令下发的token
    @FormUrlEncoded
    @POST("/oauth/token")
    @Headers({"Content-Type:application/x-www-form-urlencoded", "Authorization:Basic Ymxlc3NlZDpibGVzc2Vk"})
    Flowable<RespRemoteToken> getRemoteToken(@Field("grant_type") String grant_type,@Field("username") String username,@Field("password") String password,@Field("scope") String scope/*@Body ReqRemoteToken reqRemoteToken*/);

    //获取紧急联系人信息接口
    @POST("/damagetothelibrary/add")
    Flowable<HttpResult> uploadRepairInfo(@Body ReqRepairInfo reqRepairInfo);

    //获取主联系人信息接口
    @POST("/userandcontruction/list")
    Flowable<RespPersonCenter> getMainContactInfo(@Body ReqPersonCenter reqPersonCenter);

    //获取紧急联系人信息接口
    @POST("/responsibleperson/list")
    Flowable<RespPersonCenter> getUrgentContactInfo(@Body ReqPersonCenter reqPersonCenter);

    //获取验证码
    @GET("/sms/getVerifiCode")
    Flowable<HttpResult> getVerifiCode(@Query("mobile") String mobile);

    //获取验证码
    @GET("/sms/confirm")
    Flowable<HttpResult> getPassword(@Query("mobile") String mobile,@Query("code") String code);

    //修改本人手机号
    @POST("/sms/updatePhone")
    Flowable<HttpResult> updatePhone(@Body ReqUpdatePhone reqUpdatePhone);


    //修改协同人人手机号
    @POST("/sms/updateXietongPhone")
    Flowable<HttpResult> updateXietongPhone(@Body ReqUpdatePhone reqUpdatePhone);

    //设备领用
    @POST("/userandlock/add")
    @Headers({"Content-Type:application/json"})
    Flowable<HttpResult> addDeviceAndUser(@Body ReqDeviceUse reqDeviceUse);

}
