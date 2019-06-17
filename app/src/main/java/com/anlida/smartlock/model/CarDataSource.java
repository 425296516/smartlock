package com.anlida.smartlock.model;

import com.anlida.smartlock.model.req.SearchCarListPost;
import com.anlida.smartlock.model.req.SearchCarPost;
import com.anlida.smartlock.model.req.SearchListPagePost;
import com.anlida.smartlock.model.resp.CarInfo;
import com.anlida.smartlock.model.resp.DataBean;

import java.util.List;

import io.reactivex.Flowable;

public interface CarDataSource {
    //精确搜索车牌号
    Flowable<List<CarInfo>> searchCar(SearchCarPost post);

    //车辆列表 locateFragment 管理员
    Flowable<List<CarInfo>> searchCarList(SearchCarListPost post);

    //司机 locateFragment绑定的车辆
    Flowable<HttpResult<List<CarInfo>>> searchCarDriver();
    //carListActivity 获取所有车辆列表
    Flowable<HttpResult<DataBean>> searchCatListAllPage(SearchListPagePost post);
}
