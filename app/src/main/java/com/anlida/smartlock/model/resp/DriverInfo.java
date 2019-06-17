package com.anlida.smartlock.model.resp;

public class DriverInfo {
    private long drivingTime;
    private double drivenDistance;
    private String name;
    private String mobile;
    private String headImg;
    private String vehicleStatus;
    private String pic;
    private int remainingBattery;

    public long getDrivingTime() {
        return drivingTime;
    }

    public double getDrivenDistance() {
        return drivenDistance;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public String getPic() {
        return pic;
    }

    public int getRemainingBattery() {
        return remainingBattery;
    }
}
