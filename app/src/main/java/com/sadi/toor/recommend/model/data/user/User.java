package com.sadi.toor.recommend.model.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("custom_token")
    @Expose
    private String customAccessToken;

    @SerializedName("code")
    @Expose
    private int code;

    public String getCustomAccessToken() {
        return customAccessToken;
    }

    public int getCode() {
        return code;
    }
}
