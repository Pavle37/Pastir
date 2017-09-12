package com.pastir.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.activity.MainActivity;
import com.pastir.R;
import com.pastir.model.ActionBar;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.storage.DataSource;

/**
 * Unites common functionality of all fragments
 */

public abstract class BaseFragment extends Fragment{

    public static final String ARGS_KEY = "com.pastir.bundle";
    public MainActivity mActivity;

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
        ab.setLeftImage(R.drawable.logo1);
        return ab;
    }

    public ActionBarPresenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize all stuff
        View view = init(inflater,container);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK)
                {
                    if(event.getAction() == KeyEvent.ACTION_DOWN && getPresenter() != null) {
                        getPresenter().onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    protected abstract View init(LayoutInflater inflater, ViewGroup container);

    /**
     * Calls the same name method from BaseActivity class
     * @param fragmentToLoad fragment that's going to be loaded instead of current one
     */
    public void loadFragment(BaseFragment fragmentToLoad){
        mActivity.loadFragment(fragmentToLoad);
    }

    /**
     * Gets the navigation id for the right menu for the MainActivity
     * @return id of the navigation item or PREVIOUSLY_SELECTED which doesn't change the selected item
     */
    public int getNavigationId(){
        return MainActivity.PREVIOUSLY_SELECTED;
    }

}
