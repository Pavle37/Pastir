package com.pastir.fragment;

/**
 * Created by Creitive 31 on 02-Mar-18.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;

/**
 * A fragment containing a list of books.
 */
public class BooksFragment extends Fragment {

    public BooksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }
}
