package com.anlida.smartlock.network.exception;

import android.content.Context;
import android.util.Log;


import com.anlida.component.ExitEvent;
import com.anlida.smartlock.config.Constant;
import com.anlida.smartlock.ui.user.LoginActivity;
import com.anlida.smartlock.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.HttpException;

public class ApiErrorHelper {
    private static final String TAG = "FM";

    public static final String NET_ERROR_CODE = "00001";

    /**
     * 统一的异常处理
     *
     * @param context
     * @param throwable
     */
    public static void handleException(Context context, Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        ToastUtils.show(context, "未知错误");
    }

    public static void handlerNetError(Context context) {
        ToastUtils.show(context, "网络请求失败，请稍后重试");
    }

    /**
     * 处理自定义的异常
     *
     * @param context
     * @param exception
     */
    public static void handleApiException(Context context, ApiException exception) {
//        ErrorCodeUtils.getErrorInfo(context,exception.getErrorCode(),exception.getErrorMessage());
                ToastUtils.show(context, exception.getErrorMessage());
                String code=exception.getErrorCode();
                if(Constant.CODE_TOKEN_INVALID.equals(code)){
                    LoginActivity.start(context);
                    EventBus.getDefault().post(new ExitEvent());
                }


    }

    /**
     * 适配后台接口数据不一致的情况
     */
    private static void handleNullException(Context context) {
        ToastUtils.show(context, "暂无数据");
    }

    public static void handleGlobalException(Context context, Throwable t) {
        if (t instanceof HttpException || t instanceof IOException) {
            handlerNetError(context);
        } else if (t instanceof ApiException) {
            handleApiException(context, (ApiException) t);
        } else if (t instanceof NullPointerException) {
            handleNullException(context);
        } else {
            ApiErrorHelper.handleException(context, t);
        }
    }


}
