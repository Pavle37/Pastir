package com.pastir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MorningVerse implements ListItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("text")
    @Expose
    private String text;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns position of the item in the list
     * @param morningVerses list of morning verses
     * @param id id of the item that's being looked for
     * @return
     */
    public static int getPositionForId(List<MorningVerse> morningVerses, int currentId) {
        for(int i = 0 ; i < morningVerses.size(); i++){
            if(morningVerses.get(i).getId().equals(currentId))
                return i;
        }
        return -1;
    }
}
