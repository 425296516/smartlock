package com.anlida.smartlock.model.req;

import java.util.List;

public class ImieList {

    private List<String> imei;

    public ImieList(List<String> imei) {
        this.imei = imei;
    }

    public List<String> getImei() {
        return imei;
    }

    public void setImei(List<String> imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "ImieList{" +
                "imei=" + imei +
                '}';
    }
}
