package com.pastir.model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creitive 31 on 02-Mar-18.
 */

public class Chapter {
    private int number;

    private String subtitle;
    private Integer numberOfVerses;
    private String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus rhoncus lacinia commodo. Quisque ultrices nulla fermentum est lacinia convallis. Cras lobortis dolor risus, ut vehicula nunc sollicitudin at. Sed imperdiet fermentum eros eu laoreet. Nullam eu nisi et odio luctus egestas a a nunc. Nullam ut egestas leo. Cras volutpat mauris mi, a venenatis neque posuere nec. Donec leo purus, ullamcorper ac lorem eu, sagittis tempor augue.";

    public Chapter() {
    }

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getNumberOfVerses() {
        return numberOfVerses;
    }

    public void setNumberOfVerses(Integer numberOfVerses) {
        this.numberOfVerses = numberOfVerses;
    }

    public static Chapter parse(DataSnapshot snapshot, int chapterId) {
        Chapter chapter = new Chapter();
        chapter.setText(snapshot.child("verses").getValue(String.class));
        chapter.setSubtitle(snapshot.child("subtitle").getValue(String.class));
        chapter.setNumberOfVerses(snapshot.child("number_of_verses").getValue(Integer.class));
        chapter.setNumber(chapterId);
        return chapter;
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

    public static List<Chapter> getMockedSecond() {
        List<Chapter> list = new ArrayList<>();
        list.add(new Chapter(1));
        list.add(new Chapter(2));
        list.add(new Chapter(3));
        list.add(new Chapter(4));
        list.add(new Chapter(5));
        list.add(new Chapter(6));
        return list;
    }

    public static List<Chapter> getMockedThird() {
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
        list.add(new Chapter(10));
        list.add(new Chapter(11));
        list.add(new Chapter(12));
        list.add(new Chapter(13));
        list.add(new Chapter(14));
        list.add(new Chapter(15));
        list.add(new Chapter(16));
        list.add(new Chapter(17));
        list.add(new Chapter(18));
        return list;
    }
}
