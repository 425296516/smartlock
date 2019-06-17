package com.anlida.smartlock.utils;

import android.content.Context;

/**
 * Created by (张慈瑞).
 * 错误码工具类
 */

public class ErrorCodeUtils {

    private String errorCode;

    public ErrorCodeUtils(String errorCode) {
        this.errorCode = errorCode;
    }

    public static void getErrorInfo(Context context, String errorCode) {

        getErrorInfo(context, errorCode, "接口返回数据失败");
    }

    public static void getErrorInfo(Context context, String errorCode, String content) {
        switch (errorCode) {
            case "10000":
                ToastUtils.show(context, "成功");
                break;
            case "10001":
                ToastUtils.show(context, "用户输入参数非法");
                //ExitUtils.exit(context);
                break;

            default:
                ToastUtils.show(context, content + "");

                break;
        }
    }
}
