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
    public static void setImageResource(ImageView iv, int resId){
        iv.setImageResource(resId);
    }

    @BindingAdapter("bind:html")
    public static void bindHtml(View view, String text) {
        Utils.View.setHtmlToView((TextView) view, text);
    }
}
