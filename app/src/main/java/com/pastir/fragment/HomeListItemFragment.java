package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentHomeListItemBinding;
import com.pastir.model.ActionBar;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.HomeListItemPresenter;

/**
 * Fragment used to show all events
 */
public class HomeListItemFragment extends BaseFragment {


    private RecyclerView rvHomeListItems;
    private HomeListItemPresenter mPresenter;

    public HomeListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeListItemBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_list_item, container, false);
        View view = binding.getRoot();

        rvHomeListItems = (RecyclerView) view.findViewById(R.id.rvHomeListItems);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvHomeListItems.setLayoutManager(lm);


        mPresenter = new HomeListItemPresenter();
        mPresenter.bindView(this);

        binding.setPresenter(mPresenter);

        return view;
    }

    @Override
    public ActionBarPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab =  super.setActionBar();
        ab.setTitle(getString(R.string.events));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        return ab;
    }
}
