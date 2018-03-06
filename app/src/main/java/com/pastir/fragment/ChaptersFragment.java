package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.pastir.storage.DataSource;

import java.util.List;

/**
 * A fragment containing a list of chapters from chosen book.
 */
public class ChaptersFragment extends BaseFragment {


    private ChaptersPresenter mPresenter;
    private GridView gvChapters;
    private int mSelectedBook;
    private FragmentChaptersBinding mBinding;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(inflater, container);
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false);
        View view = mBinding.getRoot();

        mSelectedBook = getArguments().getInt(ARGS_KEY);
        gvChapters = view.findViewById(R.id.gvChapters);

        mPresenter = new ChaptersPresenter();
        mPresenter.bindView(this);
        mBinding.setPresenter(mPresenter);




        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            setAdapter(DataSource.getInstance().getBible().get(mSelectedBook).getChapters(), null);

            Book selected = DataSource.getInstance().getBible().get(mSelectedBook);
            setAdapter(selected.getChapters(), null);
            mBinding.setBook(selected);
        }
    }


    public void setAdapter(List<Chapter> chapters, OnListItemClickListener listener) {
        ChapterItemAdapter adapter = new ChapterItemAdapter(getContext(), chapters, listener);
        gvChapters.setAdapter(adapter);

        gvChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                loadFragment(ChapterOverviewFragment.getInstance(mSelectedBook, position));
            }
        });
    }

    public static BaseFragment getInstance(int selectedBook) {
        BaseFragment instance = new ChaptersFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY, selectedBook);
        instance.setArguments(args);
        return instance;
    }

    public void setSelectedBook(int selectedBook) {
        mSelectedBook = selectedBook;
    }
}
