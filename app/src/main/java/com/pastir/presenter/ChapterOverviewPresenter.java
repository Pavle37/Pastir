package com.pastir.presenter;

import com.pastir.fragment.ChapterOverviewFragment;

public class ChapterOverviewPresenter extends ActionBarPresenter<ChapterOverviewFragment> {

    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }
}
