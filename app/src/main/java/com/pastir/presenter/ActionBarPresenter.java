package com.pastir.presenter;

import com.pastir.activity.MainActivity;
import com.pastir.fragment.BaseFragment;

/**
 * Base class for handling actionbar events like back pressed, etc...
 */

public class ActionBarPresenter<V> extends BasePresenter<V> {
    public void onBackPressed() {
        ((BaseFragment) getView()).getActivity().finish();
    }

    public void onMenuPressed() {
        ((MainActivity) ((BaseFragment) getView()).getActivity()).openDrawerIfClosed();
    }

    public void openCalendar() {

    }

    public void openCloud() {

    }
}
