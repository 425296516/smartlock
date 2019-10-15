package com.anlida.smartlock.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.anlida.smartlock.utils.DataWarehouse;


public class LocationService extends Service {
    private static final String TAG = LocationService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");

        initLocation();
    }


    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    private AMapLocationClient mLocationClient;

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //设置精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(10000);//每10秒钟查询一次定位

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    DataWarehouse.setLatitude(aMapLocation.getLatitude());
                    DataWarehouse.setLongitude(aMapLocation.getLongitude());
                }
            }
        }
    };


    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
        Log.d(TAG,"Service:onDestroy");
    }
}