package com.pastir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotivationalSticker implements HomeListItem{

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getPlace() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}