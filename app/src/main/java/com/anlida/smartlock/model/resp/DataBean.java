package com.anlida.smartlock.model.resp;

import java.util.List;

/**
 * Crate by E470PD on 2019/4/16
 */
public class DataBean {
    private int total;
    private int pages;
    private int pageSize;
    private List<CarInfo> list;

    public List<CarInfo> getList() {
        return list;
    }

    public int getTotal() {
        return total;
    }

}
