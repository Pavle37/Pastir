package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentHomeListItemBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.HomeListItemPresenter;
import com.pastir.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used to show all events
 */
public class HomeListItemFragment extends BaseFragment {


    private HomeFragment.Slider mSlider;
    private RecyclerView rvHomeListItems;
    private HomeListItemPresenter mPresenter;

    public HomeListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list_item, container, false);
        View view = binding.getRoot();

        rvHomeListItems = (RecyclerView) view.findViewById(R.id.rvHomeListItems);

        mSlider = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), HomeFragment.Slider.class);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvHomeListItems.setLayoutManager(lm);


        mPresenter = new HomeListItemPresenter();
        mPresenter.bindView(this);

        binding.setPresenter(mPresenter);

        mPresenter.loadData(mSlider);

        return view;
    }

    public void setAdapter(List<? extends ListItem> homeListItems,OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.image_list_item_fullscreen, homeListItems, listener);
        rvHomeListItems.setAdapter(adapter);
    }

    @Override
    public ActionBarPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        if (mSlider== HomeFragment.Slider.EVENTS) ab.setTitle(getString(R.string.events));
        else ab.setTitle(getString(R.string.motivational_messages));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setBackButtonText(getString(R.string.home));
        ab.setMorningVersesActionBar(false);
        return ab;
    }

    public static BaseFragment getInstance(HomeFragment.Slider slider){
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(slider));
        BaseFragment instance = new HomeListItemFragment();
        instance.setArguments(args);
        return instance;
    }
}
