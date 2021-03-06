package com.pastir.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pastir.R;
import com.pastir.databinding.FragmentMorningVerseOverviewBinding;
import com.pastir.databinding.FragmentPlaceholderMorningVerseBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.MorningVerse;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.MorningVersesPresenter;
import com.pastir.storage.DataSource;

import java.util.List;

/**
 * Used to show morning verse view pager and to handle audio stream
 */
public class MorningVerseOverviewFragment extends BaseFragment {

    private ViewPager mViewPager;
    private MorningVersesPresenter mPresenter;

    public MorningVerseOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        final FragmentMorningVerseOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_morning_verse_overview, container, false);

        String currentDate = getArguments().getString(ARGS_KEY);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mPresenter = new MorningVersesPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        List<MorningVerse> morningVerses = DataSource.getInstance().getMorningVerses();
        int position = MorningVerse.getPositionForId(currentDate);
        MorningVerse currentVerse = morningVerses.get(position);
        binding.setVerse(currentVerse);
        mPresenter.loadVerseAudio(currentVerse);

        // Set up the ViewPager with the sections adapter.
        mViewPager = binding.getRoot().findViewById(R.id.vpVerses);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onDestroy();//Clear the MediaPlayer
                MorningVerse verse = DataSource.getInstance().getMorningVerses().get(position);
                binding.setVerse(verse);
                mPresenter.loadVerseAudio(verse);//Reinitialize it
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return binding.getRoot();
    }


    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.morning_verses));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setCalendarBarVisible(true);
        ab.setTabLayoutVisible(false);
        return ab;
    }

    public static BaseFragment getInstance(String date) {
        BaseFragment instance = new MorningVerseOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, date);
        instance.setArguments(args);
        return instance;
    }

    public void scrollViewPagerRight() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void scrollViewPagerLeft() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

    public void setViewPagerItem(MorningVerse morningVerse) {
        mViewPager.setCurrentItem(MorningVerse.getPositionForId(morningVerse.getDate()));
    }

    public Handler getHandler() {
        return new Handler();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.openCloud();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            FragmentPlaceholderMorningVerseBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_placeholder_morning_verse, container, false);
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            MorningVerse morningVerse = DataSource.getInstance().getMorningVerses().get(sectionNumber);
            binding.setMorningVerse(morningVerse);

            setOnLongViewPressListener(binding.getRoot(), morningVerse);

            return binding.getRoot();
        }

        private void setOnLongViewPressListener(View root, MorningVerse morningVerse) {
            LinearLayout llMorningVerseOverview = root.findViewById(R.id.llMorningVerseOverview);
            TextView tvMorningVerseText = root.findViewById(R.id.tvMorningVerse);
            llMorningVerseOverview.setOnLongClickListener(view -> {

                String shareBody = tvMorningVerseText.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, morningVerse.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Text"));
                return true;
            });
        }
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return DataSource.getInstance().getMorningVerses().size();
        }

    }


}
