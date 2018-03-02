package com.pastir.presenter;

import com.pastir.fragment.BooksFragment;
import com.pastir.model.Book;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.util.Utils;


public class BooksPresenter extends BasePresenter<BooksFragment> implements OnListItemClickListener {
    @Override
    public void bindView(BooksFragment view) {
        super.bindView(view);
        getView().setAdapter(Book.getMocked(), this);
    }

    @Override
    public void onItemClicked(ListItem item) {
        Utils.SingleToast.show(getContext(), "Clicked item");
    }
}
