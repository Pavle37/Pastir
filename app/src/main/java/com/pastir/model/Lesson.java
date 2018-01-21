package com.pastir.model;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pastir.storage.DataSource;

import java.util.ArrayList;
import java.util.List;

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

    private List<SubLesson> subLessons;

    public Lesson() {
        this.subLessons = new ArrayList<>();
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

    public List<SubLesson> getSubLessons() {
        return subLessons;
    }

    public void setSubLessons(List<SubLesson> subLessons) {
        this.subLessons = subLessons;
    }

    public static Lesson parse(DataSnapshot snapshot) {
        Lesson verse = new Lesson();
        verse.setTitle(snapshot.child("title").getValue(String.class));
        verse.setFromDate(snapshot.child("from_date").getValue(String.class));
        verse.setToDate(snapshot.child("to_date").getValue(String.class));
        verse.setVerse(snapshot.child("verse").getValue(String.class));
        for (DataSnapshot child :snapshot.child("sublessons").getChildren()){
            verse.getSubLessons().add(SubLesson.parse(child, verse.getSubLessons().size()));
        }
        return verse;
    }

    /**
     * Returns position of the item in the list
     * @param fromDate date when lesson starts
     * @return
     */
    public static int getPositionForId(String fromDate) {
        List<Lesson> lessons = DataSource.getInstance().getLessons();
        for(int i = 0 ; i < lessons.size(); i++){
            if(lessons.get(i).getFromDate().equals(fromDate))
                return i;
        }
        return -1;
    }

}
