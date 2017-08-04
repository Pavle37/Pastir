package com.pastir.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.pastir.BR;

/**
 * ActionBar represents an object that corresponds to the actionbar look while the app is running
 */

public class ActionBar extends BaseObservable {

    private String mTitle;
    private int mLeftImage;
    private boolean backButtonVisible;
    private String backButtonText;
    private boolean morningVersesActionBar;


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
    public String getBackButtonText() {
        return backButtonText;
    }

    public void setBackButtonText(String backButtonText) {
        this.backButtonText = backButtonText;
        notifyPropertyChanged(BR.backButtonText);
    }

    @Bindable
    public boolean isMorningVersesActionBar() {
        return morningVersesActionBar;
    }

    public void setMorningVersesActionBar(boolean morningVersesActionBar) {
        this.morningVersesActionBar = morningVersesActionBar;
        notifyPropertyChanged(BR.morningVersesActionBar);
    }
}
