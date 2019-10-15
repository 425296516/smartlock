package com.anlida.smartlock;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.anlida.smartlock.service.LocationService;
import com.anlida.smartlock.utils.SPUtils;
import com.blankj.utilcode.util.Utils;

public class APP extends Application {
    private static final String TAG = APP.class.getSimpleName();

    public static APP mContext;

    private int mCount;

    @Override
    public void onCreate() {
        super.onCreate();

        SPUtils.init(this);
        Utils.init(this);
        //ACRA.init(this);
        mContext = this;

        checkAppIsForeground();

    }
    private Intent serviceIntent;

    //判断APP是否处于前台
    public void checkAppIsForeground() {
        mCount = 0;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //Log.d(TAG,"onActivityCreated=" + activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //Log.d(TAG,"onActivityStarted=" + activity.getComponentName().getClassName());
                mCount++;
                //如果mCount==1，说明是从后台到前台
                if (mCount == 1) {
                    //执行app跳转到前台的逻辑
                    //开启获取车辆信息的定时服务
                    serviceIntent = new Intent(getApplicationContext(), LocationService.class);
                    startService(serviceIntent);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                //Log.d(TAG,"onActivityResumed=" + activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                //Log.d(TAG,"onActivityPaused=" + activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //Log.d(TAG,"onActivityStopped=" + activity.getComponentName().getClassName());
                mCount--;
                //如果mCount==0，说明是前台到后台
                if (mCount == 0) {
                    //执行应用切换到后台的逻辑
                    stopService(serviceIntent);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                //Log.d(TAG,"onActivitySaveInstanceState=" + activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //Log.d(TAG,"onActivityDestroyed=" + activity.getComponentName().getClassName());
            }
        });
    }


    /**
     * 获得上下文
     */
    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
