package com.anlida.smartlock;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.anlida.smartlock.utils.SPUtils;

public class APP extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(this);
        SPUtils.init(this);

    }

    public static Context getContext() {
        return context;
    }
}
