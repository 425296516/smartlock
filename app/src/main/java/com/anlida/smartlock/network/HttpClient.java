package com.anlida.smartlock.network;

import com.anlida.smartlock.R;
import com.anlida.smartlock.utils.DataWarehouse;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张慈瑞
 * 网络请求
 */

public class HttpClient {
    private static final String TAG = HttpClient.class.getSimpleName();
    public static final String BASE_URL = "http://47.111.112.30:8085/"; //测试IP
    public static final String BASE_URL_CONTROL = "http://47.111.112.30:8086/"; //测试IP
    //public static final String BASE_URL_CONTROL = "http://2o464010g0.zicp.vip:39724"; //
    public Retrofit retrofit;
    public RestAPI service;

    private HttpClient() {
        Interceptor mInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
                .header("token", DataWarehouse.getToken())
                /* .header("content-type", "application/json")*///不注销掉上传图片无法使用
                .build());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//超过时间
                .readTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                //.addInterceptor(SOCKET_TIMEOUT_INTERCEPTOR)
                //.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                //.addInterceptor(OFFLINE_INTERCEPTOR)
                //.cache(cache)
                .build();

        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(RestAPI.class);

    }


    private HttpClient(String url) {
        Interceptor mInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
                .header("Authorization",DataWarehouse.getAccessToken())
                .build());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//超过时间
                .readTimeout(10, TimeUnit.SECONDS)

                .addNetworkInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                //.addInterceptor(SOCKET_TIMEOUT_INTERCEPTOR)
                //.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                //.addInterceptor(OFFLINE_INTERCEPTOR)
                //.cache(cache)
                .build();

        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

        service = retrofit.create(RestAPI.class);

    }

    private static HttpClient instance;

    //获取单例
    public static HttpClient getInstance() {
        //return SingletonHolder.INSTANCE;
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;

    }


    //获取单例
    public static HttpClient getInstance(String url) {
        //return SingletonHolder.INSTANCE;
        //if (instance == null) {
        //instance = new HttpClient(url);
        //}
        return new HttpClient(url);
    }

    //在访问HttpMethods时创建单例
   /* private static class SingletonHolder {
        private static final HttpClient INSTANCE = new HttpClient();
    }*/

    private static final Interceptor SOCKET_TIMEOUT_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Response response = null;
            try {
                response = chain.proceed(chain.request());
            } catch (SocketTimeoutException e) {
                //网络连接异常
                ToastUtils.showShort(R.string.no_internet);
            }
            return response;
        }
    };

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            Logger.d("online request");
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 0)
                    .build();
        } else {
            return originalResponse;
        }
    };

    private static final Interceptor OFFLINE_INTERCEPTOR = chain -> {
        Request request = chain.request();

        if (!NetworkUtils.isConnected()) {
            Logger.d("rewriting request");

            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    };

}
