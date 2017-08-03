package com.pastir.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentHomeBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.ListItem;
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
    private RecyclerView rvEvents;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View view = binding.getRoot();

        rvMotivationalStickers = (RecyclerView) view.findViewById(R.id.rvMotivationalStickers);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);

        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMotivationalStickers.setLayoutManager(lm);
        rvMotivationalStickers.setHasFixedSize(true);

        lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvEvents.setLayoutManager(lm);
        rvEvents.setHasFixedSize(true);

        mPresenter = new HomePresenter();
        mPresenter.bindView(this);
        mPresenter.loadData();

        binding.setPresenter(mPresenter);

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

    public void setAdapter(List<? extends ListItem> motivationalStickers, OnListItemClickListener listener, Slider slider) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.image_list_item,motivationalStickers,listener);
        if(slider.equals(Slider.MOTIVATIONAL_STICKERS)) {
            rvMotivationalStickers.setAdapter(adapter);
        }
        else{
            rvEvents.setAdapter(adapter);
        }
    }


    public enum Slider{
        MOTIVATIONAL_STICKERS,
        EVENTS
    }
}
