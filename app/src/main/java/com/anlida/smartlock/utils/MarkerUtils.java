package com.anlida.smartlock.utils;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;

/**
 * Crate by E470PD on 2018/9/18
 */
public class MarkerUtils {
    private static MarkerUtils instance;

    private MarkerUtils() {
    }

    public static MarkerUtils getInstance() {
        if (instance == null) {
            instance = new MarkerUtils();
        }
        return instance;
    }

    /**
     * 添加Marker 无缩放
     *
     * @param aMap    地图对象
     * @param point   经纬度
     * @param context contex
     * @param id      要设置的Marker 图标
     * @param object  设置对象信息 这里把当前车辆信息放进去
     * @return 返回Marker对象
     */
    public Marker addMarker(AMap aMap, LatLng point, Context context, int id, Object object) {
        MarkerOptions options = new MarkerOptions();
        options.position(point);
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), id)));
        options.anchor(0.5f, 0.5f);
        Marker marker = aMap.addMarker(options);
        marker.setObject(object);
        return marker;
    }

    /**
     * 添加带缩放动画的marker 要重新播放动画可以调用{@link #playAnimation(Marker)}
     *
     * @param aMap           地图对象
     * @param markerPosition 经纬度
     * @param context        context
     * @param id             要显示的Marker图标
     * @param animTime       动画展示的时间 一般1000ms 即可
     * @param isDrag         是否可拖拽
     * @return
     */
    public Marker addGrowMarker(AMap aMap, boolean isDrag, LatLng markerPosition, Context context, int id, long animTime) {
        MarkerOptions options = new MarkerOptions();
        options.position(markerPosition);
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), id)));
        Marker marker = aMap.addMarker(options);
        Animation markerAnimation = new ScaleAnimation(0, 1, 0, 1); //初始化生长效果动画
        markerAnimation.setDuration(1000);  //设置动画时间 单位毫秒
        marker.setAnimation(markerAnimation);
        marker.setDraggable(isDrag);
        marker.startAnimation();
        return marker;
    }

    /**
     * playAnimation 再次启动marker动画
     * 配合{@link #addGrowMarker(AMap, boolean, LatLng, Context, int, long)} 使用
     *
     * @param marker
     * @return
     */
    public void playAnimation(Marker marker) {
        marker.startAnimation();
    }
}
