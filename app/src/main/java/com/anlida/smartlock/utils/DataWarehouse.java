package com.anlida.smartlock.utils;

import android.text.TextUtils;

/**
 * Created by (张慈瑞).
 * 数据仓库，对全局变量进行操作
 */
public class DataWarehouse {

    // 登陆成功之后保存
    public static final String SP_LoginStatus = "loginStatus";                  // 用户是否登录
    public static final String SP_Token = "token";                              // 用户token
    public static final String SP_AccessToken = "access_token";                 // 控制命令下发的toke
    public static final String SP_Latitude = "latitude";                        // 纬度
    public static final String SP_Longitude = "longitude";                      // 经度
    public static final String SP_ACCOUNT = "account";                          // 账号
    public static final String SP_PASSWORD = "password";                        // 密码
    public static final String SP_USERID = "userId";                            // 登录人id

    /**
     * 保存用户userId
     */
    public static void setUserId(String userId) {

        SPUtils.saveSP(SP_USERID, userId);
    }

    /**
     * 获取用户userId
     */
    public static String getUserId() {
        return SPUtils.readSp(SP_USERID);

    }

    /**
     * /**
     * 设置是否登录
     */
    public static void setLogin(boolean isLogin) {

        SPUtils.saveBooleanSP(SP_LoginStatus, isLogin);
    }

    /**
     * 获取是否登录的信息
     */
    public static boolean isLogin() {
        return SPUtils.readBooleanSp(SP_LoginStatus, false);
    }

    /**
     * 保存用户token
     */
    public static void setToken(String token) {

        SPUtils.saveSP(SP_Token, token);
    }

    /**
     * 获取用户token
     */
    public static String getToken() {
        return SPUtils.readSp(SP_Token);

    }

    /**
     * 保存用户access_token
     */
    public static void setAccessToken(String accessToken) {

        SPUtils.saveSP(SP_AccessToken, accessToken);
    }

    /**
     * 获取用户access_token
     */
    public static String getAccessToken() {
        return SPUtils.readSp(SP_AccessToken);

    }

    /**
     * 设置纬度
     */
    public static void setLatitude(double latitude) {

        SPUtils.saveSP(SP_Latitude, latitude + "");
    }

    /**
     * 获取纬度
     */
    public static double getLatitude() {
        if (!TextUtils.isEmpty(SPUtils.readSp(SP_Latitude))) {
            return Double.parseDouble(SPUtils.readSp(SP_Latitude));
        }

        return 39.90960456049752;
    }

    /**
     * 设置经度
     */
    public static void setLongitude(double longitude) {

        SPUtils.saveSP(SP_Longitude, longitude + "");
    }

    /**
     * 获取经度
     */
    public static double getLongitude() {

        if (!TextUtils.isEmpty(SPUtils.readSp(SP_Longitude))) {
            return Double.parseDouble(SPUtils.readSp(SP_Longitude));
        }

        return 116.3972282409668;
    }

    /**
     * 保存用户account
     */
    public static void setAccount(String account) {

        SPUtils.saveSP(SP_ACCOUNT, account);
    }

    /**
     * 获取用户account
     */
    public static String getAccount() {
        return SPUtils.readSp(SP_ACCOUNT);

    }

    /**
     * 保存用户密码
     */
    public static void setPassword(String password) {

        SPUtils.saveSP(SP_PASSWORD, password);
    }

    /**
     * 获取用户密码
     */
    public static String getPassword() {
        return SPUtils.readSp(SP_PASSWORD);

    }

}
