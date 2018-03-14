package com.pastir.model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent single book chapter
 */

public class Chapter {
    private int number;

    private String subtitle;
    private String text;
    private String audioPath;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public static Chapter parse(DataSnapshot snapshot, int chapterId) {
        Chapter chapter = new Chapter();
        chapter.setText(snapshot.child("verses").getValue(String.class));
        chapter.setSubtitle(snapshot.child("subtitle").getValue(String.class));
        chapter.setAudioPath(snapshot.child("audio_path").getValue(String.class));
        chapter.setNumber(chapterId);
        return chapter;
    }

}
