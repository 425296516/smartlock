package com.anlida.smartlock.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Crate by E470PD on 2018/12/28
 */
public class FmBarUtils {
    private static FmBarUtils instance;

    private FmBarUtils() {
    }

    public static FmBarUtils getInstance() {
        if (instance == null) {
            instance = new FmBarUtils();
        }
        return instance;
    }

    /**
     * @param activity
     * @param isChangeDark 是否深色主题
     */
    public void changeBar(Activity activity, boolean isChangeDark) {
        if (isChangeDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //实现状态栏图标和文字颜色为暗色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置状态栏文字颜色及图标为浅色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
