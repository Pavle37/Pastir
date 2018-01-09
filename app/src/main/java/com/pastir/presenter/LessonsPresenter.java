package com.pastir.presenter;

import com.pastir.fragment.BaseFragment;
import com.pastir.fragment.LessonsFragment;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.model.OnListItemsLoadedListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.List;

/**
 * Created by Creitive 31 on 25-Dec-17.
 */

public class LessonsPresenter extends ActionBarPresenter<BaseFragment>  implements OnListItemClickListener, OnListItemsLoadedListener {
    public void loadData() {
        mDataSource.getLessons(this);
    }

    @Override
    public void onItemClicked(ListItem item) {

    }

    @Override
    public void onListItemsLoaded(List<? extends ListItem> items) {
        if (items != null && items.size() > 0)  //Check which fragment it is and set adapter on it
            ((LessonsFragment) getView()).setAdapter(items, this);
    }
}
