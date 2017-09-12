package com.pastir.fragment;


import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pastir.R;
import com.pastir.adapter.ListItemAdapter;
import com.pastir.databinding.FragmentHomeBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;
import com.pastir.presenter.HomePresenter;
import com.pastir.util.Utils;

import java.io.IOException;
import java.util.List;

/**
 * Fragment representing home screen
 */
public class HomeFragment extends BaseFragment {

    private static final String RADIO_URL = "http://rvs.crestin.tv:8014/radioglasnade";
    private HomePresenter mPresenter;

    private RecyclerView rvMotivationalStickers;
    private RecyclerView rvEvents;

    private MediaPlayer mPlayer;
    private ImageView ivPlay;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();

        ivPlay = (ImageView) view.findViewById(R.id.ivPlay);

        rvMotivationalStickers = (RecyclerView) view.findViewById(R.id.rvMotivationalStickers);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);

        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMotivationalStickers.setLayoutManager(lm);
        rvMotivationalStickers.setHasFixedSize(true);

        lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvEvents.setLayoutManager(lm);
        rvEvents.setHasFixedSize(true);

        if (mPresenter == null) {
            mPresenter = new HomePresenter();
            mPresenter.bindView(this);
            initializeMediaPlayer();
        }
        mPresenter.loadData();

        binding.setPresenter(mPresenter);

        return view;
    }
    /*////////////////////////////////////
     * Player radio
     *////////////////////////////////////
    private void initializeMediaPlayer() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(RADIO_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPlaying() {
        mPlayer.prepareAsync();
        ivPlay.setEnabled(false);
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mPlayer.start();
                        ivPlay.setEnabled(true);
                    }
                });
        Utils.SingleToast.show(getContext(), R.string.buffering_content);
    }

    public void stopPlaying() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            initializeMediaPlayer();
        }
    }

    @Override
    public HomePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getNavigationId() {
        return R.id.nav_home;
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.home));
        ab.setBackButton(false);
        ab.setMorningVersesActionBar(false);
        return ab;
    }

    public void setAdapter(List<? extends ListItem> motivationalStickers, OnListItemClickListener listener, Slider slider) {
        ListItemAdapter adapter = new ListItemAdapter(R.layout.image_list_item, motivationalStickers, listener);
        if (slider.equals(Slider.MOTIVATIONAL_STICKERS)) {
            rvMotivationalStickers.setAdapter(adapter);
        } else {
            rvEvents.setAdapter(adapter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaying();
    }

    public enum Slider {
        MOTIVATIONAL_STICKERS,
        EVENTS
    }
}
