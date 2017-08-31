package com.pastir.presenter;


import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.media.MediaPlayer;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.pastir.BR;
import com.pastir.R;
import com.pastir.fragment.BaseFragment;
import com.pastir.fragment.CloudDialog;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.model.Cloud;
import com.pastir.model.ListItem;
import com.pastir.model.MorningVerse;
import com.pastir.model.OnListItemClickListener;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Used to handle interactions with the morning verses fragment
 */
public class MorningVersesPresenter extends ActionBarPresenter<BaseFragment> implements OnListItemClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private static final String TAG_CALENDAR = "com.pastir.dialog_calendar";
    private static final String TAG_CLOUD = "com.pastir.dialog_cloud";

    private MediaPlayer mPlayer;

    private Player mPlayingMode = Player.STOPPED;


    @Override
    public void onItemClicked(ListItem item) {
        getView().loadFragment(MorningVerseOverviewFragment.getInstance(((MorningVerse) item).getId()));
    }

    public void loadData() {
        if (mDataSource.getMorningVerses() != null)
            onMorningVersesLoaded(mDataSource.getMorningVerses());
        //TODO: Create call to WebController to fetch the data
    }

    private void onMorningVersesLoaded(List<MorningVerse> morningVerses) {
        ((MorningVersesFragment) getView()).setAdapter(morningVerses, this);
    }

    public void openCalendar() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ResourcesCompat.getColor(getView().getResources(), R.color.colorPrimary, null));
        dpd.show(getView().mActivity.getFragmentManager(), TAG_CALENDAR);
    }

    public void openCloud() {
        Cloud cloud = new Cloud();
        cloud.setSunrize("7:20");
        cloud.setSunset("16:40");
        CloudDialog fragment = CloudDialog.getInstance(cloud);
        fragment.show(getView().getFragmentManager(), TAG_CLOUD);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
        month++;
        String date = dayOfMonth + "." + (month < 10 ? "0" + month : "" + month) + "." + year;
        for (MorningVerse morningVerse : DataSource.getInstance().getMorningVerses()) {
            if (morningVerse.getDate().equals(date)) {
                if (!isInOverViewMode())
                    getView().loadFragment(MorningVerseOverviewFragment.getInstance(morningVerse.getId()));
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
            mPlayer = MediaPlayer.create(getContext(), R.raw.genesis);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setPlayingMode(Player.STOPPED);
                    mPlayingMode = Player.PLAYING;
                    ((MorningVerseOverviewFragment)getView()).scrollViewPagerRight();
                }
            });
        }
        startPlaying();
    }

    public void onLeftArrowClicked() {
        ((MorningVerseOverviewFragment) getView()).scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
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
        if(mPlayer != null)
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
        if (playerMode == Player.PLAYING && switcher.getCurrentView() instanceof ImageView){
            switcher.showPrevious();
        }
        else if(switcher.getCurrentView() instanceof LinearLayout){
            switcher.showNext();
        }
    }

    /**
     * Used only in case when we want to play the next verse
     */
    public void continuePlaying(final MorningVerse verse) {
        mPlayingMode = Player.STOPPED; //Resets to stop so that we can load the audio file from next verse
        ((MorningVerseOverviewFragment)getView()).getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onPlayClicked(verse);
            }
        },500);
    }

    public enum Player {
        PLAYING,
        PAUSED,
        STOPPED
    }
}
