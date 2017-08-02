package com.pastir.presenter;

import com.pastir.fragment.BaseFragment;

/**
 * Base class for handling actionbar events like back pressed, etc...
 */

public abstract class ActionBarPresenter<V> extends BasePresenter<V> {
    public void onBackPressed() {
        //Ignore
    }

    public void onMenuPressed() {
        ((BaseFragment) getView()).getActivity().openOptionsMenu();
    }
}
