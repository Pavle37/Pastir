package com.pastir.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pastir.R;
import com.pastir.databinding.FragmentBibleBinding;
import com.pastir.model.ActionBar;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.BiblePresenter;

/**
 * Fragment used for bible reading
 */

public class BibleFragment extends BaseFragment {

    private BiblePresenter mPresenter;

    public BibleFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        FragmentBibleBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bible, container, false);
        View view = binding.getRoot();
        mPresenter = new BiblePresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        ViewPager viewPager = view.findViewById(R.id.vp_bible);

        // Create an adapter that knows which fragment should be shown on each page
        BibleFragmentPagerAdapter adapter = new BibleFragmentPagerAdapter(getFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return binding.getRoot();
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.bible));
        ab.setBackButton(false);
        ab.setCalendarBarVisible(false);
        return ab;
    }

    @Override
    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

    @Override
    public int getNavigationId() {
        return R.id.nav_bible;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class BibleFragmentPagerAdapter extends FragmentPagerAdapter {

        public BibleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return BooksFragment.newInstance(getString(R.string.books));
            } else if (position == 1) {
                return BooksFragment.newInstance(getString(R.string.chapters));
            } else if (position == 2) {
                return BooksFragment.newInstance(getString(R.string.verses));
            } else {
                return BooksFragment.newInstance(getString(R.string.verse));
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return getString(R.string.books);
                case 1:
                    return getString(R.string.chapters);
                case 2:
                    return getString(R.string.verses);
                case 3:
                    return getString(R.string.verse);
                default:
                    return null;
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class BooksFragment extends Fragment {

        private static final String ARG_SECTION_NAME = "section_name";

        public BooksFragment() {
        }

        public static BooksFragment newInstance(String sectionName) {
            BooksFragment fragment = new BooksFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NAME, sectionName);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_books, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_SECTION_NAME));
            return rootView;
        }
    }
}
