package com.pastir.presenter;

import com.pastir.fragment.BooksFragment;
import com.pastir.model.Book;
import com.pastir.model.ListItem;
import com.pastir.model.OnBookSelectedListener;
import com.pastir.model.OnListItemClickListener;
import com.pastir.model.OnListItemsLoadedListener;

import java.util.List;


public class BooksPresenter extends BasePresenter<BooksFragment> implements OnListItemClickListener, OnListItemsLoadedListener {
    @Override
    public void bindView(BooksFragment view) {
        super.bindView(view);
        mDataSource.getBible(this);
    }

    @Override
    public void onItemClicked(ListItem item) {
        OnBookSelectedListener listener = getView().getListener();
        listener.onBookSelected(mDataSource.getBible().indexOf(item));
    }


    @Override
    public void onListItemsLoaded(List<? extends ListItem> bible) {
        if (bible != null && bible.size() > 0)
            getView().setAdapter((List<Book>) bible, this);
    }
}
