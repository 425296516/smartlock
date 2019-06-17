package com.anlida.smartlock.network.api;




import com.anlida.smartlock.network.client.ClientHelper;
import com.anlida.smartlock.network.client.Network;

import retrofit2.Retrofit;

public class ApiService {

    public static final String BASE_URL = "http://47.111.112.30:8085/"; //测试IP
    //public static final String BASE_URL = "http://40.73.25.204"; //
    private static CarService carService;

    private static CommonService commonService;

    private static UserService userService;

    private static RankingService rankingService;

    public static CarService createCarService() {
        if (carService == null) {
            carService = retrofit(BASE_URL).create(CarService.class);
        }
        return carService;
    }

    public static CommonService createCommonService() {
        if (commonService == null) {
            commonService = retrofit(BASE_URL).create(CommonService.class);
        }
        return commonService;
    }

    public static UserService createUserService() {
        if (userService == null) {
            userService = retrofit(BASE_URL).create(UserService.class);
        }
        return userService;
    }

    public static RankingService createRankingService() {
        if (rankingService == null) {
            rankingService = retrofit(BASE_URL).create(RankingService.class);
        }
        return rankingService;
    }

    private static Retrofit retrofit(String baseUrl) {
        return new Network.Builder()
                .baseUrl(baseUrl)
                .client(ClientHelper.getOkHttpClient())
                .build()
                .retrofit();
    }

}
