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
import com.pastir.util.Utils;

/**
 * Fragment used to show single event
 */
public class EventOverviewFragment extends BaseFragment {

    private Event mEvent;

    public EventOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEventOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_overview, container, false);

        mEvent = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), Event.class);

        binding.setRvItem(mEvent);

        return binding.getRoot();
    }

    public static BaseFragment getInstance(Event event) {
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(event));
        BaseFragment instance = new EventOverviewFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(mEvent.getTitle());
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setBackButtonText(getString(R.string.events));
        return ab;
    }

}