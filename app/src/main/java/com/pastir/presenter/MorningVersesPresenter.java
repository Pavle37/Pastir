package com.pastir.presenter;

import android.view.View;

import com.pastir.R;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.model.ListItem;
import com.pastir.model.MorningVerses;
import com.pastir.model.OnListItemClickListener;
import com.pastir.util.Utils;

import java.util.List;

/**
 * Created by Creitive 31 on 04-Aug-17.
 */

public class MorningVersesPresenter extends ActionBarPresenter<MorningVersesFragment> implements OnListItemClickListener {


    public void openCalendar(){
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    public void openCloud(){
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }
    @Override
    public void onItemClicked(ListItem item) {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    public void loadData() {
        if(mDataSource.getMorningVerses() != null)
            onMorningVersesLoaded(mDataSource.getMorningVerses());
        //TODO: Create call to WebController to fetch the data
    }

    private void onMorningVersesLoaded(List<MorningVerses> morningVerses) {
        getView().setAdapter(morningVerses,this);
    }
}
