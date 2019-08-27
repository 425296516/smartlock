package com.anlida.smartlock.model.req;

public class ReqDealWarning {

    private String id;
    private String updateBy;
    private String status;

    public ReqDealWarning(String id, String updateBy,String status) {
        this.id = id;
        this.updateBy = updateBy;
        this.status = status;
    }
}
