package com.anlida.smartlock.model.req;

public class ReqAirQuality {

    private String createBy;
    private String city;
    private String latitude;
    private String longitude;

    public ReqAirQuality(String createBy, String city,String latitude,String longitude) {
        this.createBy = createBy;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
