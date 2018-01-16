package com.pastir.fragment;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
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
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentMorningVersesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_morning_verses, container, false);
        View view = binding.getRoot();

        rvMorningVerses = view.findViewById(R.id.rvMorningVerses);

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
        ab.setCalendarBarVisible(true);
        return ab;
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

    @Override
    public int getNavigationId() {
        return R.id.nav_morning_verses;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mPresenter.openCloud();
        }
    }
}
