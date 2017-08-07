package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentMorningVerseOverviewBinding;
import com.pastir.databinding.FragmentPlaceholderMorningVerseBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.MorningVerse;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.MorningVerseOverviewPresenter;
import com.pastir.storage.DataSource;

/**
 * Used to show morning verse view pager and to handle audio stream
 */
public class MorningVerseOverviewFragment extends BaseFragment {

    private ViewPager mViewPager;
    private MorningVerseOverviewPresenter mPresenter;

    public MorningVerseOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMorningVerseOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_morning_verse_overview, container, false);

        int currentIndex = getArguments().getInt(ARGS_KEY);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mPresenter = new MorningVerseOverviewPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) binding.getRoot().findViewById(R.id.vpVerses);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(currentIndex);

        return binding.getRoot();
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.morning_verses));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setBackButtonText(getString(R.string.back));
        ab.setMorningVersesActionBar(true);
        return ab;
    }

    public static BaseFragment getInstance(int position) {
        BaseFragment instance = new MorningVerseOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY, position);
        instance.setArguments(args);
        return instance;
    }

    public void scrollViewPagerRight() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void scrollViewPagerLeft() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    @Override
    public ActionBarPresenter getPresenter() {
        return mPresenter;
    }

    public void setViewPagerItem(MorningVerse morningVerse) {
        mViewPager.setCurrentItem(morningVerse.getId());
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

            binding.setMorningVerse(DataSource.getInstance().getMorningVerses().get(sectionNumber));

            return binding.getRoot();
        }
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
