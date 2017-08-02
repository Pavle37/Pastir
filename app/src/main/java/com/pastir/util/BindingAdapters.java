package com.pastir.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

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
}
