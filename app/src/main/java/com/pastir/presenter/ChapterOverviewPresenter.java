package com.pastir.presenter;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.media.MediaPlayer;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.pastir.BR;
import com.pastir.fragment.ChapterOverviewFragment;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.model.Chapter;
import com.pastir.model.MorningVerse;
import com.pastir.util.EventDispatcher;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChapterOverviewPresenter extends ActionBarPresenter<ChapterOverviewFragment> {

    private MediaPlayer mPlayer;
    private Player mPlayingMode = Player.STOPPED;
    private boolean isReady = false;
    private boolean isPaused = false;
    private boolean hasCompleted = false;

    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }

    public void onLeftArrowClicked() {
        //setPlayingMode(LessonsPresenter.Player.STOPPED);
        getView().scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
        //setPlayingMode(LessonsPresenter.Player.STOPPED);
        getView().scrollViewPagerRight();
    }

    public void onPlayClicked() {
        startPlaying();
    }

    public void stopPlaying() {
        mPlayer.pause();
        mPlayer.seekTo(0);
        setPlayingMode(Player.STOPPED);
    }

    public void pausePlaying() {
        mPlayer.pause();
        setPlayingMode(Player.PAUSED);
    }

    /**
     * This method will start playing on the second run
     */
    private void startPlaying() {
        playerReady();
        isReady = true;
    }

    public void onPause() {
        isPaused = true;
    }

    public void onDestroy() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            pausePlaying();
        }
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
            setPlayingMode(Player.STOPPED);
            mPlayingMode = Player.FINISHED;
            hasCompleted = true;
            scrollNextIfResumed();
        });
    }

    /**
     * Depending on params hasCompleted and isPaused either scrolls or waits for onResume to scroll to
     * next element in the adapter
     */
    private void scrollNextIfResumed() {
        if (!isPaused && hasCompleted) {
             getView().scrollViewPagerRight();
            hasCompleted = false;
        }
    }

    private void playerReady() {
        if (isReady) {
            mPlayer.start();
            setPlayingMode(Player.PLAYING);
        }
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


    public void loadChapterAudio(Chapter currentChapter) {
        try {
            initializeMediaPlayer(currentChapter.getAudioPath());
            addPhoneCallEventHandler();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void addPhoneCallEventHandler() {
        EventDispatcher.sPhoneCallListener.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        state -> {
                            if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                                //Incoming call or dialing, active or on hold: Pause music
                                if (mPlayingMode == Player.PLAYING)
                                    mPlayer.pause();
                            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                                //Not in call: Play music
                                if (mPlayingMode == Player.PLAYING)
                                    mPlayer.start();
                            }
                        }
                );
    }

    public void onResume() {
        isPaused = false;
        scrollNextIfResumed();
    }


    public enum Player {
        PLAYING,
        PAUSED,
        STOPPED,
        FINISHED
    }
}
