package com.anlida.smartlock.model.req;

public class ReqSearchWarning {
    int pageNum;
    int pageSize;
    private String content;
    private int status;

    public ReqSearchWarning(int pageNum, int pageSize, String content, int status) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.content = content;
        this.status = status;
    }
}
