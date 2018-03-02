package com.pastir.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;

/**
 * A fragment containing a list of chapters from chosen book.
 */
public class ChaptersFragment extends Fragment {

    public ChaptersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }
}
