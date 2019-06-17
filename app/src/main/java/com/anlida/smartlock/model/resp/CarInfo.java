package com.anlida.smartlock.model.resp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Crate by E470PD on 2019/4/15
 */
public class CarInfo implements Parcelable {

    /**
     * userId : 0CAA5B01AA1A4F3B9A358B37E61C487E
     * name : 司机名字
     * mobile : 13324567890
     * vehicleState : 1   1,行驶2,停驶,3充电,4,故障
     * remainingBattery : 30
     * plateNo : 京ACC999
     * busGroup : 第二公交集团
     * pic :  http://42.159.92.113/tsp-app-h5/20190103101335.html
     * lon : 130.456
     * lat : 34.676
     */

    private String userId;
    private String name;
    private String mobile;
    @SerializedName("vehicleStatus")
    private String vehicleState;
    private int remainingBattery;
    private String plateNo;
    private String busGroup;
    private String pic;
    private double lon;
    private double lat;
    private long drivingTime;
    private double drivenDistance;
    private String vin;
    private double sumKm;
    private double energyConsumption;
    private double kmEnergyConsumption;
    private double drivingBehavior;

    public double getSumKm() {
        return sumKm;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public double getKmEnergyConsumption() {
        return kmEnergyConsumption;
    }

    public double getDrivingBehavior() {
        return drivingBehavior;
    }

    public long getDrivingTime() {
        return drivingTime;
    }

    public double getDrivenDistance() {
        return drivenDistance;
    }

    public String getVin() {
        return vin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }

    public int getRemainingBattery() {
        return remainingBattery;
    }

    public void setRemainingBattery(int remainingBattery) {
        this.remainingBattery = remainingBattery;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getBusGroup() {
        return busGroup;
    }

    public void setBusGroup(String busGroup) {
        this.busGroup = busGroup;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.vehicleState);
        dest.writeInt(this.remainingBattery);
        dest.writeString(this.plateNo);
        dest.writeString(this.busGroup);
        dest.writeString(this.pic);
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
        dest.writeLong(this.drivingTime);
        dest.writeDouble(this.drivenDistance);
        dest.writeString(this.vin);
        dest.writeDouble(this.sumKm);
        dest.writeDouble(this.energyConsumption);
        dest.writeDouble(this.kmEnergyConsumption);
        dest.writeDouble(this.drivingBehavior);
    }

    public CarInfo() {
    }

    protected CarInfo(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.vehicleState = in.readString();
        this.remainingBattery = in.readInt();
        this.plateNo = in.readString();
        this.busGroup = in.readString();
        this.pic = in.readString();
        this.lon = in.readDouble();
        this.lat = in.readDouble();
        this.drivingTime = in.readLong();
        this.drivenDistance = in.readDouble();
        this.vin = in.readString();
        this.sumKm = in.readDouble();
        this.energyConsumption = in.readDouble();
        this.kmEnergyConsumption = in.readDouble();
        this.drivingBehavior = in.readDouble();
    }

    public static final Creator<CarInfo> CREATOR = new Creator<CarInfo>() {
        @Override
        public CarInfo createFromParcel(Parcel source) {
            return new CarInfo(source);
        }

        @Override
        public CarInfo[] newArray(int size) {
            return new CarInfo[size];
        }
    };
}
