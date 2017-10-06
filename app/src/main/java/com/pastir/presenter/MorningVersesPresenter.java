package com.pastir.presenter;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.pastir.BR;
import com.pastir.R;
import com.pastir.fragment.BaseFragment;
import com.pastir.fragment.CloudDialog;
import com.pastir.fragment.HomeFragment;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.model.Event;
import com.pastir.model.ListItem;
import com.pastir.model.MorningVerse;
import com.pastir.model.OnListItemClickListener;
import com.pastir.model.OnListItemsLoadedListener;
import com.pastir.model.Results;
import com.pastir.network.WebController;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Used to handle interactions with the morning verses fragment
 */
public class MorningVersesPresenter extends ActionBarPresenter<BaseFragment> implements OnListItemClickListener, DatePickerDialog.OnDateSetListener, OnListItemsLoadedListener {

    private static final String TAG_CALENDAR = "com.pastir.dialog_calendar";
    private static final String TAG_CLOUD = "com.pastir.dialog_cloud";

    private MediaPlayer mPlayer;

    private Player mPlayingMode = Player.STOPPED;


    @Override
    public void onItemClicked(ListItem item) {
        getView().loadFragment(MorningVerseOverviewFragment.getInstance(((MorningVerse) item).getDate()));
    }

    public void loadData() {
        if (mDataSource.getMorningVerses() != null)
            mDataSource.getMorningVerses(this);
    }

    public void openCalendar() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(ResourcesCompat.getColor(getView().getResources(), R.color.colorPrimary, null));
        dpd.show(getView().mActivity.getFragmentManager(), TAG_CALENDAR);
    }

    public void openCloud() {
        if (ActivityCompat.checkSelfPermission(getView().mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getView().mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            getView().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 37);

            return;
        }
        //After we are sure we have the permissions, then execute this code
        LocationManager lm = (LocationManager) getView().mActivity.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //If we did not catch that, try with network
        if (location == null) location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Nothing, release a toast
        if (location == null) {
            Utils.SingleToast.show(getContext(), R.string.no_location);
            return;
        }
        WebController.loadSunriseSunset(this, location);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
        month++;
        String date = (dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth) + "." + (month < 10 ? "0" + month : "" + month) + "." + year;
        for (MorningVerse morningVerse : DataSource.getInstance().getMorningVerses()) {
            if (morningVerse.getDate().equals(date)) {
                if (!isInOverViewMode())
                    getView().loadFragment(MorningVerseOverviewFragment.getInstance(morningVerse.getDate()));
                else
                    ((MorningVerseOverviewFragment) getView()).setViewPagerItem(morningVerse);
                return;
            }
        }
        Utils.SingleToast.show(getContext(), R.string.no_morning_verse);
    }

    @Override
    public void onBackPressed() {
        if (isInOverViewMode())
            getView().mActivity.onBackPressed();
        else
            super.onBackPressed();
    }

    public boolean isInOverViewMode() {
        return getView() instanceof MorningVerseOverviewFragment;
    }


    public void onPlayClicked(MorningVerse verse) {
        if (mPlayingMode == Player.STOPPED) {
            mPlayer = MediaPlayer.create(getContext(), R.raw.sample);
            mPlayer.setOnCompletionListener(mp -> {
                setPlayingMode(Player.STOPPED);
                mPlayingMode = Player.FINISHED;
                ((MorningVerseOverviewFragment) getView()).scrollViewPagerRight();
            });
        }
        startPlaying();
    }

    public void onLeftArrowClicked() {
        setPlayingMode(Player.STOPPED);
        ((MorningVerseOverviewFragment) getView()).scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
        setPlayingMode(Player.STOPPED);
        ((MorningVerseOverviewFragment) getView()).scrollViewPagerRight();
    }

    public void stopPlaying() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        setPlayingMode(Player.STOPPED);
    }

    public void pausePlaying() {
        mPlayer.pause();
        setPlayingMode(Player.PAUSED);
    }

    private void startPlaying() {
        mPlayer.start();
        setPlayingMode(Player.PLAYING);
    }

    public void onPause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            pausePlaying();
        }
    }

    public void onDestroy() {
        if (mPlayer != null)
            stopPlaying();
    }


    @Bindable
    public Player getPlayingMode() {
        return mPlayingMode;
    }

    public void setPlayingMode(Player playingMode) {
        mPlayingMode = playingMode;
        notifyPropertyChanged(BR.playingMode);
    }

    @BindingAdapter("android:play")
    public static void onPlaying(ViewSwitcher switcher, Player playerMode) {
        if (playerMode == Player.PLAYING && switcher.getCurrentView() instanceof ImageView) {
            switcher.showPrevious();
        } else if (switcher.getCurrentView() instanceof LinearLayout) {
            switcher.showNext();
        }
    }

    /**
     * Used only in case when we want to play the next verse
     */
    public void continuePlaying(final MorningVerse verse) {
        mPlayingMode = Player.STOPPED; //Resets to stop so that we can load the audio file from next verse
        ((MorningVerseOverviewFragment) getView()).getHandler().postDelayed(() -> onPlayClicked(verse), 500);
    }

    public void openCloudDialog(Results results) {
        CloudDialog fragment = CloudDialog.getInstance(results);
        fragment.show(getView().getFragmentManager(), TAG_CLOUD);
    }

    @Override
    public void onListItemsLoaded(List<? extends ListItem> items) {
        if (items != null && items.size() > 0)  //Check which fragment it is and set adapter on it
            ((MorningVersesFragment)getView()).setAdapter(items, this);
    }

    public enum Player {
        PLAYING,
        PAUSED,
        STOPPED,
        FINISHED
    }
}
