package com.pastir.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.model.ActionBar;

/**
 * Fragment used to show all events
 */
public class HomeListItemFragment extends BaseFragment {


    private RecyclerView mRecyclerView;

    public HomeListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_list_item, container, false);
    }
    @Override
    protected ActionBar setActionBar() {
        ActionBar ab =  super.setActionBar();
        ab.setTitle(getString(R.string.events));
        ab.setBackButton(true);
        return ab;
    }
}
