package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentDonationsBinding;
import com.pastir.model.ActionBar;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.DonationPresenter;

/**
 * Fragment that provides user to donate resources and contact us
 */

public class DonationFragment extends BaseFragment {

    private DonationPresenter mPresenter;

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentDonationsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_donations, container, false);

        mPresenter = new DonationPresenter();
        mPresenter.bindView(this);

        binding.setPresenter(mPresenter);
        return binding.getRoot();
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.donate));
        ab.setBackButton(false);
        ab.setCalendarBarVisible(false);
        return ab;
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

}
