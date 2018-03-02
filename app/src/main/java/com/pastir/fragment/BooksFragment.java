package com.pastir.fragment;

/**
 * Created by Creitive 31 on 02-Mar-18.
 */

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentBooksBinding;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.BooksPresenter;

import java.util.List;

/**
 * A fragment containing a list of books.
 */
public class BooksFragment extends BaseFragment {

    private BooksPresenter mPresenter;
    private RecyclerView rvBooks;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentBooksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books, container, false);
        View view = binding.getRoot();

        rvBooks = view.findViewById(R.id.rvBooks);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvBooks.setLayoutManager(lm);

        mPresenter = new BooksPresenter();
        mPresenter.bindView(this);

        binding.setPresenter(mPresenter);
        return binding.getRoot();
    }

    public void setAdapter(List<? extends ListItem> morningVerses, OnListItemClickListener listener) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.book_list_item, morningVerses, listener);
        rvBooks.setAdapter(adapter);
    }
}
