package com.pastir.presenter;

import com.pastir.fragment.HomeFragment;
import com.pastir.model.ListItem;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnListItemClickListener;
import com.pastir.util.Utils;

import java.util.List;

/**
 * Used to handle interactions with the home fragment
 */

public class HomePresenter extends ActionBarPresenter<HomeFragment> implements OnListItemClickListener {

    public void loadData() {
        if(mDataSource.getMotivationalStickers() != null)
            onMotivationalStickersLoaded(mDataSource.getMotivationalStickers());
        //TODO: Create call to WebController to fetch the data
    }

    private void onMotivationalStickersLoaded(List<MotivationalSticker> motivationalStickers) {
        getView().setAdapter(motivationalStickers,this);
    }

    @Override
    public void onItemClicked(ListItem item) {
        Utils.SingleToast.show(getContext(),"Yayy :D");
    }
}
