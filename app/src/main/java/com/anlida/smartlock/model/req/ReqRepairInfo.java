package com.anlida.smartlock.model.req;

public class ReqRepairInfo {

    private String causeOfDamage;
    private String imei;
    private String createBy;

    public ReqRepairInfo(String causeOfDamage, String imei, String createBy) {
        this.causeOfDamage = causeOfDamage;
        this.imei = imei;
        this.createBy = createBy;
    }

}
