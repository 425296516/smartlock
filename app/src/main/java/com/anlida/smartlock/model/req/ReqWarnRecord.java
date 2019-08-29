package com.anlida.smartlock.model.req;

public class ReqWarnRecord {

    int pageNum;
    int pageSize;
    private String updateBy;

    public ReqWarnRecord(int pageNum, int pageSize,String updateBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.updateBy = updateBy;
    }
}
