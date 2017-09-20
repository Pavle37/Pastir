package com.pastir.presenter;

import android.databinding.Bindable;
import android.media.MediaPlayer;

import com.pastir.BR;
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

import java.io.IOException;
import java.util.List;

/**
 * Used to handle interactions with the home fragment
 */

public class HomePresenter extends ActionBarPresenter<HomeFragment> implements OnListItemClickListener, OnHomeListItemsLoadedListener {

    private static final String RADIO_URL = "http://rvs.crestin.tv:8014/radioglasnade";

    private boolean isPlaying = false;
    private MediaPlayer mPlayer;
    private boolean isReady = false;

    public void loadData() {
        mDataSource.getMotivationalStickers(this);
        mDataSource.getEvents(this);
    }

    @Override
    public void bindView(HomeFragment view) {
        super.bindView(view);
        initializeMediaPlayer();
    }

    @Override
    public void onItemClicked(ListItem item) {
        if (item instanceof Event) {
            getView().loadFragment(EventOverviewFragment.getInstance((Event) item));
        } else {
            MotivationalStickerDialogActivity.show(getView().mActivity,(MotivationalSticker)item);
        }
    }


    public void onSeeAllMotivationClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.MOTIVATIONAL_STICKERS));
    }

    public void onSeeAllEventsClicked() {
        getView().loadFragment(HomeListItemFragment.getInstance(HomeFragment.Slider.EVENTS));
    }

    public void onPlayClicked() {
        //Change the state
        setPlaying(!isPlaying);
        //Check the state for according action
        if (isPlaying){
            startPlaying();
        }
        else {
            stopPlaying();
        }

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

    /*////////////////////////////////////
     * Player radio
     *////////////////////////////////////
    private void initializeMediaPlayer() {
        //Create
        mPlayer = new MediaPlayer();
        //Set source
        try {
            mPlayer.setDataSource(RADIO_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Prepare
        mPlayer.prepareAsync();
        mPlayer.setOnPreparedListener(mp -> {
            playerReady();
            isReady = true;
        });
    }

    private void playerReady() {
        if(isReady){
            mPlayer.start();
        }
    }

    private void startPlaying() {
        playerReady();
        isReady = true;
    }

    private void stopPlaying() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    public void onDestroy(){
        if(mPlayer.isPlaying())
            mPlayer.stop();
        mPlayer.release();
    }
}
