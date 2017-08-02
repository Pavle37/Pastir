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
    private int mRightImage;


    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public int getRightImage() {
        return mRightImage;
    }

    public void setRightImage(int rightImage) {
        mRightImage = rightImage;
        notifyPropertyChanged(BR.rightImage);
    }


    @BindingAdapter("android:src")
    public static void setImageResource(ImageView iv, int resId){
        iv.setImageResource(resId);
    }


}
