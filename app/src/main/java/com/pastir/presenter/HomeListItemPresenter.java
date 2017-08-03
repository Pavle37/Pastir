package com.pastir.presenter;

import com.pastir.R;
import com.pastir.fragment.HomeListItemFragment;
import com.pastir.util.Utils;

/**
 * Created by Creitive 29 on 8/3/2017.
 */

public class HomeListItemPresenter extends ActionBarPresenter<HomeListItemFragment>
{
    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }
}
