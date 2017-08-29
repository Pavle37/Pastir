package com.pastir.presenter;

import android.databinding.Bindable;

import com.pastir.BR;
import com.pastir.fragment.EventOverviewFragment;
import com.pastir.fragment.HomeFragment;
import com.pastir.fragment.HomeListItemFragment;
import com.pastir.fragment.MotivationalStickerDialog;
import com.pastir.model.Event;
import com.pastir.model.HomeListItem;
import com.pastir.model.ListItem;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnHomeListItemsLoadedListener;
import com.pastir.model.OnListItemClickListener;

import java.util.List;

/**
 * Used to handle interactions with the home fragment
 */

public class HomePresenter extends ActionBarPresenter<HomeFragment> implements OnListItemClickListener, OnHomeListItemsLoadedListener {

    private static final String TAG = "motivational_sticker";

    private boolean isPlaying = false;

    public void loadData() {
        mDataSource.getMotivationalStickers(this);
        mDataSource.getEvents(this);
    }

    @Override
    public void onItemClicked(ListItem item) {
        if (item instanceof Event) {
            getView().loadFragment(EventOverviewFragment.getInstance((Event) item));
        } else {
            MotivationalStickerDialog fragment = MotivationalStickerDialog.getInstance((MotivationalSticker) item);
            getView().setMotivationalStickerDialog(fragment);
            fragment.setParent(getView());
            fragment.show(getView().getFragmentManager(), TAG);
        }
    }


    public void onSeeAllMotivationClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.MOTIVATIONAL_STICKERS));
    }

    public void onSeeAllEventsClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.EVENTS));
    }

    public void onPlayClicked() {
        setPlaying(!isPlaying);

        if (isPlaying) getView().startPlaying();
        else getView().stopPlaying();

    }

    @Bindable
    public boolean getIsPlaying() {
        return isPlaying;
    }

    private void setPlaying(boolean playing) {
        isPlaying = playing;
        notifyPropertyChanged(BR.isPlaying);
    }

    @Override
    public void onHomeListItemsLoaded(List<? extends HomeListItem> items) {
        if (items != null && items.size() > 0) {
            if(items.get(0) instanceof Event)
                getView().setAdapter(items, this, HomeFragment.Slider.EVENTS);

            else
                getView().setAdapter(items, this, HomeFragment.Slider.MOTIVATIONAL_STICKERS);
        }
    }
}
