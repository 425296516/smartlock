package com.anlida.smartlock.model.req;

public class BindPost {
    private String vin;
    private String type;//1绑定2解绑

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setType(String type) {
        this.type = type;
    }
}
