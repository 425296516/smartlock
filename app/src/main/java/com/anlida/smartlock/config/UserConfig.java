package com.anlida.smartlock.config;

import com.anlida.smartlock.model.resp.UserInfo;
import com.anlida.smartlock.utils.DataWarehouse;

public class UserConfig {

    private static UserInfo userInfo;

    public static void setUserInfo(UserInfo info){
        userInfo=info;
        DataWarehouse.setToken(info.getToken());
    }

    public static UserInfo getUserInfo(){
        return userInfo;
    }
}
