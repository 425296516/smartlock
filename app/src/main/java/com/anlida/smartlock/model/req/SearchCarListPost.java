package com.anlida.smartlock.model.req;

/**
 * Crate by E470PD on 2019/4/15
 */
public class SearchCarListPost {

    public String lat;
    public String lon;
    public String diagonalLat;//右上角纬度
    public String diagonalLon;//右上角经度

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setDiagonalLat(String diagonalLat) {
        this.diagonalLat = diagonalLat;
    }

    public void setDiagonalLon(String diagonalLon) {
        this.diagonalLon = diagonalLon;
    }
}
