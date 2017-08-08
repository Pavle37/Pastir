package com.pastir.presenter;

import com.pastir.MainActivity;
import com.pastir.R;
import com.pastir.fragment.BaseFragment;
import com.pastir.util.Utils;

/**
 * Base class for handling actionbar events like back pressed, etc...
 */

public class ActionBarPresenter<V> extends BasePresenter<V> {
    public void onBackPressed() {
        //Ignore
    }

    public void onMenuPressed() {
        ((MainActivity)((BaseFragment) getView()).getActivity()).openDrawerIfClosed();
    }

    public void openCalendar(){

    }

    public void openCloud(){

    }
}
