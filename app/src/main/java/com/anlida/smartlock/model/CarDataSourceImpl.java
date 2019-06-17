package com.anlida.smartlock.model;

import com.anlida.smartlock.model.req.SearchCarListPost;
import com.anlida.smartlock.model.req.SearchCarPost;
import com.anlida.smartlock.model.req.SearchListPagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DataBean;
import com.anlida.smartlock.network.api.ApiService;

import java.util.List;

import io.reactivex.Flowable;

public class CarDataSourceImpl implements CarDataSource {


    @Override
    public Flowable<List<CarInfo>> searchCar(SearchCarPost post) {
        return ApiService.createCarService().searchCar(post).
                flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<List<CarInfo>> searchCarList(SearchCarListPost post) {
        return ApiService.createCarService().searchCarList(post).
                flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<HttpResult<List<CarInfo>>> searchCarDriver() {
        return ApiService.createCarService().searchCarDriver();
    }

    @Override
    public Flowable<HttpResult<DataBean>> searchCatListAllPage(SearchListPagePost post) {
        return ApiService.createCarService().searchCatListAllPage(post);
    }
}
