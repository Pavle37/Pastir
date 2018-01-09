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
import com.pastir.databinding.FragmentLessonsBinding;
import com.pastir.databinding.FragmentMorningVersesBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.LessonsPresenter;
import com.pastir.presenter.MorningVersesPresenter;

import java.util.List;

/**
 * Created by Creitive 31 on 25-Dec-17.
 */

public class LessonsFragment extends BaseFragment {

    private LessonsPresenter mPresenter;
    private RecyclerView rvLessons;

    public LessonsFragment() {
        // Required empty public constructor
    }


    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentLessonsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lessons, container, false);
        View view = binding.getRoot();

        rvLessons = view.findViewById(R.id.rvLessons);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvLessons.setLayoutManager(lm);

        mPresenter = new LessonsPresenter();
        mPresenter.bindView(this);
        mPresenter.loadData();

        binding.setPresenter(mPresenter);

        return binding.getRoot();
    }

    public void setAdapter(List<? extends ListItem> morningVerses, OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.lessons_list_item, morningVerses, listener);
        rvLessons.setAdapter(adapter);
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.lesson));
        ab.setBackButton(false);
        ab.setMorningVersesActionBar(true);
        return ab;
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

    @Override
    public int getNavigationId() {
        return R.id.nav_lesson;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mPresenter.openCloud();
        }
    }
}
