package com.pastir.presenter;

import com.pastir.fragment.ChapterOverviewFragment;

public class ChapterOverviewPresenter extends ActionBarPresenter<ChapterOverviewFragment> {

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
}
