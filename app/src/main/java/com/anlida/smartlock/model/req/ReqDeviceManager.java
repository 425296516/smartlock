package com.anlida.smartlock.model.req;

public class ReqDeviceManager {
    int pageNum;
    int pageSize;
    String content;
    String createBy;

    public ReqDeviceManager(int pageNum, int pageSize, String content, String createBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.content = content;
        this.createBy = createBy;
    }
}
