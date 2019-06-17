
package com.anlida.smartlock.network.client;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;

import com.anlida.smartlock.BuildConfig;
import com.anlida.smartlock.R;
import com.anlida.smartlock.utils.DataWarehouse;
import com.orhanobut.logger.Logger;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ClientHelper {
    private static final String TAG = "ClientHelper";
    private static final long CONNECTION_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 10;

    private ClientHelper() {
        throw new IllegalStateException("don't allow initialization");
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getHeaderInterceptor());
        if (BuildConfig.logging) {
            builder.addInterceptor(getLoggingInterceptor());
        }
        return builder.build();
    }

    /**
     * 统一设置请求头
     *
     * @return
     */
    public static Interceptor getHeaderInterceptor() {
        Log.d(TAG, "getHeaderInterceptor: "+DataWarehouse.getToken());
        return (chain) -> chain.proceed(chain.request().newBuilder()
                .header("content-type", "application/json;charset=UTF-8")
                .header("token", DataWarehouse.getToken())
                .build());

    }

    /**
     * 拦截超时
     *
     * @return
     */
    public static Interceptor getTimeOutInterceptor() {
        return chain -> {
            Response response = null;
            try {
                response = chain.proceed(chain.request());
                Logger.d( "getTimeOutInterceptor: " + response);
            } catch (SocketTimeoutException e) {
                //网络连接异常
                com.blankj.utilcode.util.ToastUtils.showShort(R.string.no_internet);
            }
            return response;
        };
    }

    /**
     * 缓存过滤器
     *
     * @return
     */
    public static Interceptor getCacheInterceptor() {
        return chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    /**
     * 日志过滤器（为了数据安全性，建议只在debug阶段使用）
     */
    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


}
