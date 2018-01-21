package com.pastir.model;

import android.databinding.Bindable;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pastir.storage.DataSource;

import java.util.List;

public class SubLesson implements ListItem {

    private int subLessonListId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
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

    public void setSubLessonListId(int subLessonListId) {
        this.subLessonListId = subLessonListId;
    }

    public int getSubLessonListId() {
        return subLessonListId;
    }

    public static SubLesson parse(DataSnapshot snapshot, int subLessonId) {
        SubLesson verse = new SubLesson();
        verse.setSubLessonListId(subLessonId);
        verse.setTitle(snapshot.child("title").getValue(String.class));
        verse.setText(snapshot.child("text").getValue(String.class));
        verse.setDate(snapshot.child("date").getValue(String.class));
        verse.setAudioPath(snapshot.child("audio_path").getValue(String.class));
        return verse;
    }

    /**
     * Returns position of the item in the list
     *
     * @param currentDate date for the morning verse
     * @return
     */
    public static int getPositionForId(Lesson lesson, String currentDate) {
        List<SubLesson> subLessons = lesson.getSubLessons();
        for (int i = 0; i < subLessons.size(); i++) {
            if (subLessons.get(i).getDate().equals(currentDate))
                return i;
        }
        return -1;
    }

}
