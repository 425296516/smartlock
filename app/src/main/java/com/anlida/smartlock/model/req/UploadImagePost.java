package com.anlida.smartlock.model.req;

import com.google.gson.annotations.SerializedName;

public class UploadImagePost {
    @SerializedName("base64")
    private String baseImage;

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }
}
