package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pastir.R;
import com.pastir.adapter.BookSearchAdapter;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentBooksBinding;
import com.pastir.model.Book;
import com.pastir.model.ListItem;
import com.pastir.model.OnBookSelectedListener;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.BooksPresenter;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;

import java.util.List;

/**
 * A fragment containing a list of books.
 */
public class BooksFragment extends BaseFragment {

    private BooksPresenter mPresenter;
    private RecyclerView rvBooks;
    private OnBookSelectedListener mListener;
    private FloatingActionButton btnSearch;
    private MaterialSearchView searchView;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(inflater,container);
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

        initSearch(view);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        searchView.closeSearch();
    }


    private void initSearch(View view) {
        searchView = mActivity.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((BookSearchAdapter) rvBooks.getAdapter()).filter(newText);
                return false;
            }
        });

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(view1 -> {
            if (searchView.isSearchOpen())searchView.closeSearch();
            else searchView.showSearch();
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && searchView!=null) {
            searchView.closeSearch();
        }
    }

    public void setAdapter(List<Book> books, OnListItemClickListener listener) {
        BookSearchAdapter adapter = new BookSearchAdapter(books, listener,R.layout.book_list_item);
        rvBooks.setAdapter(adapter);
    }

    public void setOnBooksSelectedListener(OnBookSelectedListener listener) {
        mListener = listener;
    }

    public OnBookSelectedListener getListener() {
        return mListener;
    }


}
