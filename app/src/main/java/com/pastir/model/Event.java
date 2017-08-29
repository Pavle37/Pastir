package com.pastir.model;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event implements HomeListItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("main_image_path")
    @Expose
    private String imageMain;
    @SerializedName("thumbnail_image_path")
    @Expose
    private String imageThumbnail;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageMain() {
        return imageMain;
    }

    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Event parse(DataSnapshot snapshot) {
        Event event = new Event();
        event.setTitle(snapshot.child("title").getValue(String.class));
        event.setDescription(snapshot.child("description").getValue(String.class));
        event.setDate(snapshot.child("date").getValue(String.class));
        event.setTime(snapshot.child("time").getValue(String.class));
        event.setPlace(snapshot.child("place").getValue(String.class));
        event.setImageMain(snapshot.child("main_image_path").getValue(String.class));
        event.setImageThumbnail(snapshot.child("thumbnail_image_path").getValue(String.class));
        return event;
    }
}
