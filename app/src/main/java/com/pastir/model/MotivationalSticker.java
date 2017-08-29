package com.pastir.model;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MotivationalSticker implements HomeListItem{

    @SerializedName("main_image_path")
    @Expose
    private String imageMain;
    @SerializedName("thumbnail_image_path")
    @Expose
    private String imageThumbnail;
    @SerializedName("id")
    @Expose
    private Integer id;

    @Override
    public String getImageMain() {
        return imageMain;
    }

    @Override
    public String getImageThumbnail() {
        return imageThumbnail;
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

    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static MotivationalSticker parse(DataSnapshot snapshot) {
        MotivationalSticker sticker = new MotivationalSticker();
        sticker.setImageMain(snapshot.child("main_image_path").getValue(String.class));
        sticker.setImageThumbnail(snapshot.child("thumbnail_image_path").getValue(String.class));
        return sticker;
    }
}