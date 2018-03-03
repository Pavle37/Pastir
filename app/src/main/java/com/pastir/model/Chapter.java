package com.pastir.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creitive 31 on 02-Mar-18.
 */

public class Chapter {
    private int number;

    private String text = "Chapter text";

    public Chapter(int number) {
        this.number = number;
    }

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

    public static List<Chapter> getMocked() {
        List<Chapter> list = new ArrayList<>();
        list.add(new Chapter(1));
        list.add(new Chapter(2));
        list.add(new Chapter(3));
        list.add(new Chapter(4));
        list.add(new Chapter(5));
        list.add(new Chapter(6));
        list.add(new Chapter(7));
        list.add(new Chapter(8));
        list.add(new Chapter(9));
        return list;
    }
}
