package com.pastir.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pastir.R;

/**
 * Common binding adapters used in the app
 */

public class BindingAdapters {

    @BindingAdapter("bind:imageUrl")
    public static void bindImageUrl(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .placeholder(R.drawable.logo_nav)
                .dontAnimate()
                .fitCenter()
                .into(iv);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

    @BindingAdapter("bind:html")
    public static void bindHtml(View view, String text) {
        if(text != null)
            Utils.View.setHtmlToView((TextView) view, text);
    }

    @BindingAdapter("bind:dayInWeek")
    public static void setDayInWeek(TextView view, int subLessonListId) {
        switch (subLessonListId) {
            case 0:
                view.setText("SUBOTA");
                break;
            case 1:
                view.setText("NEDELJA");
                break;
            case 2:
                view.setText("PONEDELJAK");
                break;
            case 3:
                view.setText("UTORAK");
                break;
            case 4:
                view.setText("SREDA");
                break;
            case 5:
                view.setText("ČETVRTAK");
                break;
            case 6:
                view.setText("PETAK");
                break;
            default:
                view.setText("NEPOZNATO");
        }
    }
}
