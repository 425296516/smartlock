package com.anlida.smartlock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPUtils {
    static SharedPreferences sSharedPreferences;

    public static void init(Context context) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存SP
     *
     * @param name
     * @param content
     */
    public static void saveSP(String name, String content) {
        sSharedPreferences.edit().putString(name, content).apply();
    }

    /**
     * 保存SP
     *
     * @param name
     * @param content
     */
    public static void saveIntSP(String name, int content) {
        sSharedPreferences.edit().putInt(name, content).apply();
    }

    /**
     * 保存Boolean类型的SP
     *
     * @param name
     * @param content
     */
    public static void saveBooleanSP(String name, boolean content) {
        sSharedPreferences.edit().putBoolean(name, content).apply();
    }


    public static String readSp(String name) {
        return sSharedPreferences.getString(name, "");
    }

    public static String readSp(String name, String defValue) {
        return sSharedPreferences.getString(name, defValue);
    }

    public static int readIntSp(String name) {
        return sSharedPreferences.getInt(name, 0);
    }

    /**
     * 获取boolean类型的SP
     *
     * @param name
     * @return
     */
    public static boolean readBooleanSp(String name) {
        return sSharedPreferences.getBoolean(name, true);
    }

    /**
     * 获取boolean类型的SP
     *
     * @param name
     * @return
     */
    public static boolean readBooleanSp(String name, boolean defaultValue) {
        return sSharedPreferences.getBoolean(name, defaultValue);
    }

    /**
     * 清除保存到本地SP的数据
     */
    public static void clean(Context mContext) {
        clean();
    }

    public static void clean() {
        sSharedPreferences.edit().clear().commit();
    }
}
