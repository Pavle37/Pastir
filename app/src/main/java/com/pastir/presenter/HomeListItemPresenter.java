package com.pastir.presenter;

import com.pastir.activity.MotivationalStickerDialogActivity;
import com.pastir.fragment.EventOverviewFragment;
import com.pastir.fragment.HomeFragment;
import com.pastir.fragment.HomeListItemFragment;
import com.pastir.model.Event;
import com.pastir.model.HomeListItem;
import com.pastir.model.ListItem;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnHomeListItemsLoadedListener;
import com.pastir.model.OnListItemClickListener;

import java.util.List;

/**
 * Used to handle interactions with the home list item fragment
 */

public class HomeListItemPresenter extends ActionBarPresenter<HomeListItemFragment> implements OnListItemClickListener, OnHomeListItemsLoadedListener{
    private static final String TAG = "motivational_sticker";

    public void loadData(HomeFragment.Slider slider) {
        if (slider.equals(HomeFragment.Slider.EVENTS)) {
            mDataSource.getEvents(this);
        } else {
            mDataSource.getMotivationalStickers(this);
        }
    }

    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }

    @Override
    public void onItemClicked(ListItem item) {
        if (item instanceof Event) {
            getView().loadFragment(EventOverviewFragment.getInstance((Event) item));
        } else {
              MotivationalStickerDialogActivity.show(getView().mActivity, (MotivationalSticker) item);
        }
    }

    @Override
    public void onHomeListItemsLoaded(List<? extends HomeListItem> events) {
        getView().setAdapter(events, this);
    }
}
