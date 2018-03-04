package com.pastir.presenter;

import com.pastir.fragment.BibleFragment;

/**
 * Handles all logic related to bible screen
 */
public class BiblePresenter extends ActionBarPresenter<BibleFragment> {

    @Override
    public void onBackPressed() {
        getView().mActivity.finish();
    }
}
