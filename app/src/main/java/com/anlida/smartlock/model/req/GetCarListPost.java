package com.anlida.smartlock.model.req;

public class GetCarListPost {
    private int startPage;
    private int pageSize;
    private Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
