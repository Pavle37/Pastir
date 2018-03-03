package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentChapterOverviewBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Book;
import com.pastir.model.Chapter;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.ChapterOverviewPresenter;
import com.pastir.util.Utils;

public class ChapterOverviewFragment extends BaseFragment {

    private ChapterOverviewPresenter mPresenter;

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentChapterOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapter_overview, container, false);

        Chapter chapter = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY),Chapter.class);

        mPresenter = new ChapterOverviewPresenter();
        mPresenter.bindView(this);

        binding.setPresenter(mPresenter);
        binding.setChapter(chapter);


        return binding.getRoot();
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.bible));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setCalendarBarVisible(false);
        ab.setTabLayoutVisible(false);
        return ab;
    }

    public static BaseFragment getInstance(Chapter chapter) {
        BaseFragment instance = new ChapterOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(chapter));
        instance.setArguments(args);
        return instance;
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

}
