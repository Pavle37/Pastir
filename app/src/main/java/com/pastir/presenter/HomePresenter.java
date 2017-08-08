package com.pastir.presenter;

import com.pastir.R;
import com.pastir.fragment.EventOverviewFragment;
import com.pastir.fragment.HomeFragment;
import com.pastir.fragment.HomeListItemFragment;
import com.pastir.fragment.MotivationalStickerDialog;
import com.pastir.model.Event;
import com.pastir.model.ListItem;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnListItemClickListener;
import com.pastir.util.Utils;

import java.util.List;

/**
 * Used to handle interactions with the home fragment
 */

public class HomePresenter extends ActionBarPresenter<HomeFragment> implements OnListItemClickListener {

    private static final String TAG = "motivational_sticker";

    public void loadData() {
        if (mDataSource.getMotivationalStickers() != null)
            onMotivationalStickersLoaded(mDataSource.getMotivationalStickers());
        if (mDataSource.getEvents() != null)
            onEventsLoaded(mDataSource.getEvents());
        //TODO: Create call to WebController to fetch the data
    }

    private void onMotivationalStickersLoaded(List<MotivationalSticker> motivationalStickers) {
        getView().setAdapter(motivationalStickers, this, HomeFragment.Slider.MOTIVATIONAL_STICKERS);
    }

    private void onEventsLoaded(List<Event> events) {
        getView().setAdapter(events, this, HomeFragment.Slider.EVENTS);
    }

    @Override
    public void onItemClicked(ListItem item) {
        if (item instanceof Event) {
            getView().loadFragment(EventOverviewFragment.getInstance((Event) item));
        } else {
            MotivationalStickerDialog fragment = MotivationalStickerDialog.getInstance((MotivationalSticker) item);
            fragment.show(getView().getFragmentManager(), TAG);
        }
    }


    public void onSeeAllMotivationClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.MOTIVATIONAL_STICKERS));
    }

    public void onSeeAllEventsClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.EVENTS));
    }

    public void onPlayClicked(){
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

}
