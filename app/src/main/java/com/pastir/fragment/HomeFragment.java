package com.pastir.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.model.ActionBar;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.HomePresenter;

import java.util.List;

/**
 * Fragment representing home screen
 */
public class HomeFragment extends BaseFragment {

    private HomePresenter mPresenter;

    private RecyclerView rvMotivationalStickers;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvMotivationalStickers = (RecyclerView) view.findViewById(R.id.rvMotivationalStickers);

        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMotivationalStickers.setLayoutManager(lm);
        rvMotivationalStickers.setHasFixedSize(true);

        mPresenter = new HomePresenter();
        mPresenter.bindView(this);
        mPresenter.loadData();

        return view;
    }

    @Override
    public HomePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab =  super.setActionBar();
        ab.setTitle(getString(R.string.home));
        return ab;
    }

    public void setAdapter(List<MotivationalSticker> motivationalStickers, OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.image_list_item,motivationalStickers,listener);
        rvMotivationalStickers.setAdapter(adapter);
    }
}
