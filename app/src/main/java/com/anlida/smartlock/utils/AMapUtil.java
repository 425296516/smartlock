package com.anlida.smartlock.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by zhangcirui on 2018/3/20.
 */

public class AMapUtil {
    private static final String TAG = AMapUtil.class.getSimpleName();
    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *http://lbs.amap.com/api/uri-api/android-uri-explain/
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     *  必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context, String sourceApplication , String lat , String lon , String dev , String dirveMode){
       /* act=android.intent.action.VIEW
        cat=android.intent.category.DEFAULT
        dat=androidamap://route?sourceApplication=softname&slat=36.2&slon=116.1&sname=abc&dlat=36.3&dlon=116.2&dname=def&dev=0&m=0&t=1
        pkg=com.autonavi.minimap*/


        StringBuffer stringBuffer  = new StringBuffer("androidamap://route?sourceApplication=")
                .append(sourceApplication);

        stringBuffer.append("&dlat=").append(lat)
                .append("&dlon=").append(lon)
                .append("&dev=").append(dev)
                .append("&t=").append(dirveMode);

        Logger.d(stringBuffer.toString());
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);

         /*  // 构造导航参数
                NaviPara naviPara = new NaviPara();
                // 设置终点位置
                naviPara.setTargetPoint(marker.getPosition());
                // 设置导航策略，这里是避免拥堵
                naviPara.setNaviStyle(AMapUtils.DRIVING_NO_HIGHWAY);
                try {
                    // 调起高德地图导航
                    AMapUtils.openAMapNavi(naviPara, getApplicationContext());
                } catch (AMapException e) {
                    // 如果没安装会进入异常，调起下载页面
                    AMapUtils.getLatestAMapApp(getApplicationContext());
                }*/
    }


    public static void getToBaiduNavigation(Activity activity, String desLatitude, String desLongitude,String dirveMode){

        Intent intent = null;  //调起百度地图客户端（Android）

        String url = "intent://map/direction?destination=latlng:"+desLatitude+"," +
                ""+desLongitude+"|name:目的地&mode="+dirveMode+"&src=yourCompanyName|yourAppName#" +
                "Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";

        Logger.d(url);
        try {
            intent = Intent.getIntent(url);
            activity.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/DataBean/DataBean/" + packageName).exists();
    }
}

