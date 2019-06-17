package com.anlida.smartlock.model;

import com.anlida.smartlock.model.req.ReqRankingListBean;
import com.anlida.smartlock.model.resp.RespBusGroup;
import com.anlida.smartlock.model.resp.RespDriverRanking;
import com.anlida.smartlock.model.resp.RespProvinCity;
import com.anlida.smartlock.model.resp.RespRankingListBean;
import com.anlida.smartlock.network.api.ApiService;

import java.util.List;

import io.reactivex.Flowable;

public class RankingDataSourceImpl implements RankingDataSource {

    @Override
    public Flowable<RespDriverRanking> getDriverRanking(int type) {
        return ApiService.createRankingService().getDriverRanking(type)
                .flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<RespRankingListBean> getRankingList(ReqRankingListBean reqRankingListBean) {
        return ApiService.createRankingService().getRankingList(reqRankingListBean)
                .flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<List<RespProvinCity>> getProvinceCity() {
        return ApiService.createRankingService().getProvinceCity()
                .flatMap(result -> Flowable.just(result.getData()));
    }

    @Override
    public Flowable<List<RespBusGroup>> getBusGroup() {
        return ApiService.createRankingService().getBusGroup()
                .flatMap(result -> Flowable.just(result.getData()));
    }

}
