package com.pastir.model;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pastir.storage.DataSource;

import java.util.List;

public class MorningVerse implements ListItem {

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
    @SerializedName("audio_path")
    @Expose
    private String audioPath;

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

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public static MorningVerse parse(DataSnapshot snapshot) {
        MorningVerse verse = new MorningVerse();
        verse.setTitle(snapshot.child("title").getValue(String.class));
        verse.setText(snapshot.child("text").getValue(String.class));
        verse.setDate(snapshot.child("date").getValue(String.class));
        verse.setAudioPath(snapshot.child("audio_path").getValue(String.class));
        verse.setDescription(snapshot.child("verse").getValue(String.class));
        return verse;
    }

    /**
     * Returns position of the item in the list
     * @param currentDate date for the morning verse
     * @return
     */
    public static int getPositionForId(String currentDate) {
        List<MorningVerse> morningVerses = DataSource.getInstance().getMorningVerses();
        for(int i = 0 ; i < morningVerses.size(); i++){
            if(morningVerses.get(i).getDate().equals(currentDate))
                return i;
        }
        return -1;
    }
}
