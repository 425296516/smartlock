package com.anlida.component.utils;


import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 对activity进行管理
 * Created by jiane on 2016/9/8.
 */
public class ActivityCollector {
    public static ArrayList<Activity> activities = new ArrayList<Activity>();
    public static WeakReference<ArrayList<Activity>> weakReference = new WeakReference(activities);

    /**
     * 添加Activity
     */
    public static void addActivity(Activity activity) {
        weakReference.get().add(activity);
    }

    public static void removeActivity(Activity activity) {
        weakReference.get().remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : weakReference.get()) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 除了传来的Activity其他的全部删除
     * 可以传多个Activity
     *
     * @param clazz
     */
    public static void removeAll(Class<?>... clazz) {
        boolean isExist = false;
        for (Activity act : weakReference.get()) {
            for (Class c : clazz) {
                if (act.getClass().isAssignableFrom(c)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            } else {
                isExist = false;
            }
        }
    }


}

