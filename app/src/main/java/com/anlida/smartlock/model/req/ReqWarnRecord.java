package com.anlida.smartlock.model.req;

public class ReqWarnRecord {

    int pageNum;
    int pageSize;
    String status;

    public ReqWarnRecord(int pageNum, int pageSize,String status) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.status = status;
    }
}
