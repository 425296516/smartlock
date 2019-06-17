package com.anlida.smartlock.model.req;

public class ReqRankingListBean {

    private int type;
    private String province;
    private String city;
    private String busGroup;
    private int startPage;
    private int pageSize;

    public ReqRankingListBean(int type, String province, String city, String busGroup, int startPage, int pageSize) {
        this.type = type;
        this.province = province;
        this.city = city;
        this.busGroup = busGroup;
        this.startPage = startPage;
        this.pageSize = pageSize;
    }

}
