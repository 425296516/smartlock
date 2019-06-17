package com.anlida.smartlock.model.req;

/**
 * Crate by E470PD on 2019/4/22
 */
public class SearchListPagePost {
    private int startPage;
    private int pageSize;

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
