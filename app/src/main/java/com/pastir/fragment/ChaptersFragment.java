package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pastir.R;
import com.pastir.adapter.ChapterItemAdapter;
import com.pastir.databinding.FragmentChaptersBinding;
import com.pastir.model.Chapter;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ChaptersPresenter;

import java.util.List;

/**
 * A fragment containing a list of chapters from chosen book.
 */
public class ChaptersFragment extends BaseFragment {


    private ChaptersPresenter mPresenter;
    private GridView gvChapters;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentChaptersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false);
        View view = binding.getRoot();

        gvChapters = view.findViewById(R.id.gvChapters);

        mPresenter = new ChaptersPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        setAdapter(Chapter.getMocked(), null);


        return view;
    }

    public void setAdapter(List<Chapter> chapters, OnListItemClickListener listener) {
        ChapterItemAdapter adapter = new ChapterItemAdapter(getContext(), chapters, listener);
        gvChapters.setAdapter(adapter);

        gvChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                loadFragment(ChapterOverviewFragment.getInstance(Chapter.getMocked().get(position)));
            }
        });
    }
}
