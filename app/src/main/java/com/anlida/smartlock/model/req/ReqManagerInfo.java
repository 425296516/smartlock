package com.anlida.smartlock.model.req;

public class ReqManagerInfo {

    private String userId;
    private String address;
    private String contructionName;
    private String bys;
    private String workType;
    private String name;
    private String workId;
    private String idCard;
    private String phone;
    private String creatBy;

    public ReqManagerInfo(String userId, String address, String contructionName, String bys, String workType, String name, String workId, String idCard, String phone, String creatBy) {
        this.userId = userId;
        this.address = address;
        this.contructionName = contructionName;
        this.bys = bys;
        this.workType = workType;
        this.name = name;
        this.workId = workId;
        this.idCard = idCard;
        this.phone = phone;
        this.creatBy = creatBy;
    }
}
