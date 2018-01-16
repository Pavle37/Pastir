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
import com.pastir.fragment.LessonOverviewFragment;
import com.pastir.fragment.LessonsFragment;
import com.pastir.model.Lesson;
import com.pastir.model.SubLesson;
import com.pastir.model.ListItem;
import com.pastir.model.OnCloudClickListener;
import com.pastir.model.OnListItemClickListener;
import com.pastir.model.OnListItemsLoadedListener;
import com.pastir.model.Results;
import com.pastir.network.WebController;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Used to handle interaction with the fragments related to lessons
 */

public class LessonsPresenter extends ActionBarPresenter<BaseFragment> implements OnListItemClickListener, DatePickerDialog.OnDateSetListener, OnListItemsLoadedListener, OnCloudClickListener {

    private static final String TAG_CALENDAR = "com.pastir.dialog_calendar";
    private static final String TAG_CLOUD = "com.pastir.dialog_cloud";

    private MediaPlayer mPlayer;

    private LessonsPresenter.Player mPlayingMode = LessonsPresenter.Player.STOPPED;
    private boolean isReady = false;

    public void loadData() {
        mDataSource.getLessons(this);
    }

    @Override
    public void onItemClicked(ListItem item) {
        getView().loadFragment(LessonOverviewFragment.getInstance(((Lesson) item).getFromDate()));

    }

    @Override
    public void onListItemsLoaded(List<? extends ListItem> items) {
        if (items != null && items.size() > 0)  //Check which fragment it is and set adapter on it
            ((LessonsFragment) getView()).setAdapter(items, this);
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
        WebController.loadSunriseSunset(this, getContext(), location);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
        month++;
        String date = (dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth) + "." + (month < 10 ? "0" + month : "" + month) + "." + year + ".";
        for (Lesson lesson : DataSource.getInstance().getLessons()) {
            for (SubLesson subLesson : lesson.getSubLessons()) {
                if (subLesson.getDate().equals(date)) {
                    if (!isInOverViewMode())
                        getView().loadFragment(LessonOverviewFragment.getInstance(lesson.getFromDate()));
                    else
                        ((LessonOverviewFragment) getView()).setViewPagerItem(lesson,subLesson);
                    return;
                }
            }
        }
        Utils.SingleToast.show(getContext(), R.string.no_lesson);
    }

    @Override
    public void onBackPressed() {
        if (isInOverViewMode())
            getView().mActivity.onBackPressed();
        else
            super.onBackPressed();
    }

    public boolean isInOverViewMode() {
        return getView() instanceof LessonOverviewFragment;
    }


    public void onPlayClicked() {
        startPlaying();
    }

    public void onLeftArrowClicked() {
        setPlayingMode(LessonsPresenter.Player.STOPPED);
        ((LessonOverviewFragment) getView()).scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
        setPlayingMode(LessonsPresenter.Player.STOPPED);
        ((LessonOverviewFragment) getView()).scrollViewPagerRight();
    }

    public void stopPlaying() {
        mPlayer.pause();
        mPlayer.seekTo(0);
        setPlayingMode(LessonsPresenter.Player.STOPPED);
    }

    public void pausePlaying() {
        mPlayer.pause();
        setPlayingMode(LessonsPresenter.Player.PAUSED);
    }

    /**
     * This method will start playing on the second run
     */
    private void startPlaying() {
        playerReady();
        isReady = true;
    }

    public void onPause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            pausePlaying();
        }
    }

    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void initializeMediaPlayer(String url) {
        //Reset the isReady flag
        isReady = false;
        //Create
        mPlayer = new MediaPlayer();
        //Set source
        try {
            mPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Prepare
        mPlayer.prepareAsync();
        mPlayer.setOnPreparedListener(mp -> {
            startPlaying();
        });
        //When the playing is done
        mPlayer.setOnCompletionListener(mp -> {
            setPlayingMode(LessonsPresenter.Player.STOPPED);
            mPlayingMode = LessonsPresenter.Player.FINISHED;
            ((LessonOverviewFragment) getView()).scrollViewPagerRight();
        });
    }

    private void playerReady() {
        if (isReady) {
            mPlayer.start();
            setPlayingMode(LessonsPresenter.Player.PLAYING);
        }
    }


    @Bindable
    public LessonsPresenter.Player getPlayingMode() {
        return mPlayingMode;
    }

    public void setPlayingMode(LessonsPresenter.Player playingMode) {
        mPlayingMode = playingMode;
        notifyPropertyChanged(BR.playingMode);
    }

    @BindingAdapter("android:play")
    public static void onPlaying(ViewSwitcher switcher, LessonsPresenter.Player playerMode) {
        if (playerMode == LessonsPresenter.Player.PLAYING && switcher.getCurrentView() instanceof ImageView) {
            switcher.showPrevious();
        } else if (switcher.getCurrentView() instanceof LinearLayout) {
            switcher.showNext();
        }
    }

    /**
     * Used only in case when we want to play the next verse
     */
    public void continuePlaying() {
        mPlayingMode = LessonsPresenter.Player.STOPPED; //Resets to stop so that we can load the audio file from next verse
        ((LessonOverviewFragment) getView()).getHandler().postDelayed(this::onPlayClicked, 500);
    }

    public void openCloudDialog(Results results) {
        CloudDialog fragment = CloudDialog.getInstance(results);
        fragment.show(getView().getFragmentManager(), TAG_CLOUD);
    }

    public void loadSubLessonAudio(SubLesson currentLesson) {
        //TODO: Currently there is no correct audio path in database for subLessons
       // initializeMediaPlayer(currentLesson.getAudioPath());
    }

    public enum Player {
        PLAYING,
        PAUSED,
        STOPPED,
        FINISHED
    }
}
