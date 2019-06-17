package com.anlida.smartlock.model.req;

public class ReqDeviceUse {

    private String createBy;
    private String imei;
    private String name;
    private String workId;
    private String idCard;
    private String phone;
    private String age;
    private String bloodType;
    private String sex; //1男，2女

    public ReqDeviceUse(String createBy, String imei, String name, String workId, String idCard, String phone, String age, String bloodType,String sex) {
        this.createBy = createBy;
        this.imei = imei;
        this.name = name;
        this.workId = workId;
        this.idCard = idCard;
        this.phone = phone;
        this.age = age;
        this.bloodType = bloodType;
        this.sex = sex;
    }
}
