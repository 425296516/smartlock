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
    private String latitude;
    private String longitude;

    public ReqDeviceUse(String createBy, String imei, String name, String workId, String idCard, String phone, String age, String bloodType, String sex,String latitude,String longitude) {
        this.createBy = createBy;
        this.imei = imei;
        this.name = name;
        this.workId = workId;
        this.idCard = idCard;
        this.phone = phone;
        this.age = age;
        this.bloodType = bloodType;
        this.sex = sex;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
