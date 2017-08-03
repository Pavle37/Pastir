package com.pastir.presenter;

import com.pastir.MainActivity;
import com.pastir.fragment.BaseFragment;

/**
 * Base class for handling actionbar events like back pressed, etc...
 */

public abstract class ActionBarPresenter<V> extends BasePresenter<V> {
    public void onBackPressed() {
        //Ignore
    }

    public void onMenuPressed() {
        ((MainActivity)((BaseFragment) getView()).getActivity()).openDrawerIfClosed();
    }
}
