package com.pastir.model;


import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of book
 */

public class Book implements ListItem {

    private String name;
    private List<Chapter> chapters;

    public Book() {
        this.chapters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }


    public static Book parse(DataSnapshot snapshot) {
        Book book = new Book();
        book.setName(snapshot.child("name").getValue(String.class));
        for (DataSnapshot child :snapshot.child("chapters").getChildren()){
            book.getChapters().add(Chapter.parse(child, book.getChapters().size()));
        }
        return book;
    }

}
