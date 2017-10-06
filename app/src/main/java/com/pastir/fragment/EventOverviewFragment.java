package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentEventOverviewBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Event;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.util.Utils;

/**
 * Fragment used to show single event
 */
public class EventOverviewFragment extends BaseFragment {

    private Event mEvent;

    private ActionBarPresenter<EventOverviewFragment> mPresenter;

    public EventOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        FragmentEventOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_overview, container, false);

        mEvent = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), Event.class);

        binding.setRvItem(mEvent);

        mPresenter = new ActionBarPresenter<EventOverviewFragment>(){
            @Override
            public void onBackPressed() {
                getView().mActivity.onBackPressed();
            }
        };
        mPresenter.bindView(this);

        return binding.getRoot();
    }

    public static BaseFragment getInstance(Event event) {
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(event));
        BaseFragment instance = new EventOverviewFragment();
        instance.setArguments(args);
        return instance;
    }

    public ActionBarPresenter<EventOverviewFragment> getHomePresenter() {
        return mPresenter;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(mEvent.getTitle());
        ab.setLeftImage(0);
        ab.setBackButton(true);
        return ab;
    }

}
