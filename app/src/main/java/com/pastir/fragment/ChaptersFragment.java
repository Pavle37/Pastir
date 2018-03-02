package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentChaptersBinding;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.ChaptersPresenter;

import java.util.List;

/**
 * A fragment containing a list of chapters from chosen book.
 */
public class ChaptersFragment extends BaseFragment {


    private ChaptersPresenter mPresenter;
    private RecyclerView rvChapters;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentChaptersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false);
        View view = binding.getRoot();

        rvChapters = view.findViewById(R.id.rvChapters);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvChapters.setLayoutManager(lm);

        mPresenter = new ChaptersPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        return binding.getRoot();
    }

    public void setAdapter(List<? extends ListItem> morningVerses, OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.lessons_list_item, morningVerses, listener);
        rvChapters.setAdapter(adapter);
    }
}
