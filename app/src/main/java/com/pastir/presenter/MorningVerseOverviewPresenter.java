package com.pastir.presenter;

import com.pastir.R;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.util.Utils;

/**
 * Created by Creitive 31 on 04-Aug-17.
 */

public class MorningVerseOverviewPresenter extends ActionBarPresenter<MorningVerseOverviewFragment> {

    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }

    public void onPlayClicked() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    public void onLeftArrowClicked() {
        getView().scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
        getView().scrollViewPagerRight();
    }

    @Override
    public void openCloud() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    @Override
    public void openCalendar() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }
}
