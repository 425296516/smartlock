package com.anlida.smartlock.utils;

import android.text.TextUtils;

/**
 * Created by (张慈瑞).
 */

public class HideFormatUtils {

    /**
     * 隐藏手机号中间五位
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(mobile) && mobile.length() > 8) {
            for (int i = 0; i < mobile.length(); i++) {
                char c = mobile.charAt(i);
                if (i > 2 && i < 8) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 隐藏身份证中间八位
     * @param idcard
     * @return
     */
    public static String hideIdCard(String idcard) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(idcard) && idcard.length() > 14) {
            for (int i = 0; i < idcard.length(); i++) {
                char c = idcard.charAt(i);
                if (i > 5 && i < 14) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 隐藏姓名只保留第一位
     * @param name
     * @return
     */
    public static String hideName(String name) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(name) && name.length() > 0) {
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if (i > 0) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

}
