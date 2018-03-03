package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pastir.R;
import com.pastir.adapter.ChapterItemAdapter;
import com.pastir.databinding.FragmentChaptersBinding;
import com.pastir.model.Book;
import com.pastir.model.Chapter;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ChaptersPresenter;
import com.pastir.util.Utils;

import java.util.List;

/**
 * A fragment containing a list of chapters from chosen book.
 */
public class ChaptersFragment extends BaseFragment {


    private ChaptersPresenter mPresenter;
    private GridView gvChapters;
    private Book mSelectedBook;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentChaptersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false);
        View view = binding.getRoot();

        mSelectedBook = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), Book.class);

        gvChapters = view.findViewById(R.id.gvChapters);

        mPresenter = new ChaptersPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        setAdapter(mSelectedBook.getChapters(), null);


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            setAdapter(mSelectedBook.getChapters(), null);
        }
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

    public static BaseFragment getInstance(Book book) {
        BaseFragment instance = new ChaptersFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(book));
        instance.setArguments(args);
        return instance;
    }

    public void setSelectedBook(Book selectedBook) {
        mSelectedBook = selectedBook;
    }
}
