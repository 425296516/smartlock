package com.anlida.smartlock.model.req;

public class ReqWarnRecord {

    int pageNum;
    int pageSize;

    public ReqWarnRecord(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
