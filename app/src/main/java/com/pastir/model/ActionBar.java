package com.pastir.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.pastir.BR;

/**
 * ActionBar represents an object that corresponds to the actionbar look while the app is running
 */

public class ActionBar extends BaseObservable {


    private String mTitle;
    private int mLeftImage;
    private boolean backButtonVisible;
    private boolean calendarBarVisible;
    private boolean tabLayoutVisible;


    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public int getLeftImage() {
        return mLeftImage;
    }

    public void setLeftImage(int leftImage) {
        mLeftImage = leftImage;
        notifyPropertyChanged(BR.leftImage);
    }

    @Bindable
    public boolean isBackButtonVisible() {
        return backButtonVisible;
    }

    public void setBackButton(boolean backButtonVisible) {
        this.backButtonVisible = backButtonVisible;
        notifyPropertyChanged(BR.backButtonVisible);
    }

    @Bindable
    public boolean isCalendarBarVisible() {
        return calendarBarVisible;
    }

    public void setCalendarBarVisible(boolean calendarBarVisible) {
        this.calendarBarVisible = calendarBarVisible;
        notifyPropertyChanged(BR.calendarBarVisible);
    }

    @Bindable
    public boolean isTabLayoutVisible() {
        return tabLayoutVisible;
    }

    public void setTabLayoutVisible(boolean tabLayoutVisible) {
        this.tabLayoutVisible = tabLayoutVisible;
        notifyPropertyChanged(BR.tabLayoutVisible);

    }
}
