package com.pastir.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creitive 31 on 02-Mar-18.
 */

public class Book implements ListItem {

    private String name;
    private List<Chapter> chapters;

    public Book(String name, List<Chapter> chapters) {
        this.name = name;
        this.chapters = chapters;
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

    public static List<Book> getMocked() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("Evandjelje po Mateji", Chapter.getMocked()));
        list.add(new Book("Evandjelje po Marku", Chapter.getMockedSecond()));
        list.add(new Book("Poslanica Rimljanima", Chapter.getMockedThird()));
        return list;
    }
}
