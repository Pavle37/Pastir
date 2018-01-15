package com.pastir.model;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pastir.storage.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uros on 1/15/2018.
 */

public class Lesson implements ListItem {
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("verse")
    @Expose
    private String verse;

    private List<Sublesson> sublessons;

    public Lesson() {
        this.sublessons = new ArrayList<>();
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public List<Sublesson> getSublessons() {
        return sublessons;
    }

    public void setSublessons(List<Sublesson> sublessons) {
        this.sublessons = sublessons;
    }

    public static Lesson parse(DataSnapshot snapshot) {
        Lesson verse = new Lesson();
        verse.setTitle(snapshot.child("title").getValue(String.class));
        verse.setFromDate(snapshot.child("from_date").getValue(String.class));
        verse.setToDate(snapshot.child("to_date").getValue(String.class));
        verse.setVerse(snapshot.child("verse").getValue(String.class));
        for (DataSnapshot child :snapshot.getChildren()){
            verse.getSublessons().add(Sublesson.parse(child));
        }
        return verse;
    }



}
