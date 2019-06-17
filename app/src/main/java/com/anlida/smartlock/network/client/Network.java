package com.anlida.smartlock.network.client;




import com.anlida.smartlock.BuildConfig;
import com.anlida.smartlock.network.convert.FMGsonConverterFactory;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Network {
    private Retrofit retrofit;
    private String baseUrl;
    private OkHttpClient client;


    protected Network(){

    }

    /**
     * 构造retrofit客户端
     * @return
     */
    public Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = getBuilder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(FMGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    protected Retrofit.Builder getBuilder() {
        return new Retrofit.Builder();
    }

    public static class Builder {
        private String baseUrl;
        private OkHttpClient client;
        private Headers.Builder headerBuilder;

        /**
         * 构造Network
         * @return
         */
        public Network build() {
            Network network = new Network();
            initDefaultClient();
            network.baseUrl = baseUrl;
            network.client = client;
            return network;
        }

        /**
         * 初始化默认的okhttpClient
         */
        private void initDefaultClient() {
            if (client == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                if (BuildConfig.DEBUG) {
                    builder.addNetworkInterceptor(ClientHelper.getLoggingInterceptor());
                }
                if(headerBuilder==null){
                    headerBuilder=getDefaultHeaderBuilder();
                }
                client=builder.build();
            }


        }

        /**
         * 设置okhttpClient，满足定制化
         * @param client
         * @return
         */
        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        /**
         * 添加baseUrl
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 添加请求头
         * @param key
         * @param value
         * @return
         */
        public Builder addHeader(String key, String value) {
            if (headerBuilder == null) {
                headerBuilder = getDefaultHeaderBuilder();
            }
            headerBuilder.add(key, value);
            return this;
        }

        /**
         * 获取默认请求头构造器（根据公司业务添加通用属性，目前暂无）
         * @return
         */
        private Headers.Builder getDefaultHeaderBuilder() {
            Headers.Builder builder = new Headers.Builder();
            return builder;
        }


    }


}
