package com.anlida.smartlock.model;

import com.anlida.smartlock.model.req.ReqRankingListBean;
import com.anlida.smartlock.model.resp.RespBusGroup;
import com.anlida.smartlock.model.resp.RespDriverRanking;
import com.anlida.smartlock.model.resp.RespProvinCity;
import com.anlida.smartlock.model.resp.RespRankingListBean;

import java.util.List;

import io.reactivex.Flowable;

public interface RankingDataSource {

    //获取司机自己的排行
    Flowable<RespDriverRanking> getDriverRanking(int type);

    //获取排行榜
    Flowable<RespRankingListBean> getRankingList(ReqRankingListBean reqRankingListBean);

    //超级管理员获取省市信息
    Flowable<List<RespProvinCity>> getProvinceCity();

    //6.4 超级管理员获取公交集团列表
    Flowable<List<RespBusGroup>> getBusGroup();

}
