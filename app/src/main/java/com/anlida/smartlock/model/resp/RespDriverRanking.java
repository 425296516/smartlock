package com.anlida.smartlock.model.resp;

public class RespDriverRanking {

    private int ranking;
    private String userId;
    private String busGroup;
    private String name;
    private String headImg;
    private float data;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusGroup() {
        return busGroup;
    }

    public void setBusGroup(String busGroup) {
        this.busGroup = busGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
