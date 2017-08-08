package com.pastir.fragment;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentMorningVersesBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.MorningVersesPresenter;

import java.util.List;

/**
 * Fragment that shows list of morning verses
 */
public class MorningVersesFragment extends BaseFragment {

    private MorningVersesPresenter mPresenter;
    private RecyclerView rvMorningVerses;

    public MorningVersesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMorningVersesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_morning_verses, container, false);
        View view = binding.getRoot();

        rvMorningVerses = (RecyclerView) view.findViewById(R.id.rvMorningVerses);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvMorningVerses.setLayoutManager(lm);

        mPresenter = new MorningVersesPresenter();
        mPresenter.bindView(this);
        mPresenter.loadData();

        binding.setPresenter(mPresenter);

        return binding.getRoot();
    }

    public void setAdapter(List<? extends ListItem> morningVerses, OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.morning_verses_list_item, morningVerses, listener);
        rvMorningVerses.setAdapter(adapter);
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.morning_verses));
        ab.setBackButton(false);
        ab.setMorningVersesActionBar(true);
        return ab;
    }

    @Override
    public ActionBarPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getNavigationId() {
        return R.id.nav_morning_verses;
    }

}
