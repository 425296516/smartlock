package com.anlida.smartlock.model.req;

public class ReqUpdatePhone {

    private String id;
    private String phone;
    private String code;

    public ReqUpdatePhone(String id, String phone, String code) {
        this.id = id;
        this.phone = phone;
        this.code = code;
    }

}
