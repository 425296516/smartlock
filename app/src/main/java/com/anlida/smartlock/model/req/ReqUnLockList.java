package com.anlida.smartlock.model.req;

public class ReqUnLockList {

    int pageNum;
    int pageSize;
    private String createBy;
    private int status;

    public ReqUnLockList(int pageNum, int pageSize, String createBy, int status) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.createBy = createBy;
        this.status = status;
    }
}
