package com.pastir.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.pastir.MainActivity;
import com.pastir.R;
import com.pastir.model.ActionBar;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.storage.DataSource;

/**
 * Unites common functionality of all fragments
 */

public abstract class BaseFragment extends Fragment{

    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setCurrentFragment(this);
        setActionBar();
    }

    protected ActionBar setActionBar(){
        ActionBar ab = DataSource.getInstance().getActionBar();
        ab.setTitle(null);
        ab.setRightImage(R.drawable.logo1);
        return ab;
    }

    public ActionBarPresenter getPresenter() {
        return null;
    }
}
