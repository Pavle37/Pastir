package com.pastir.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastir.R;
import com.pastir.databinding.FragmentBibleBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Book;
import com.pastir.model.OnBookSelectedListener;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.BiblePresenter;

/**
 * Fragment used for bible reading
 */

public class BibleFragment extends BaseFragment  {

    private BiblePresenter mPresenter;
    private ViewPager viewPager;

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

        viewPager = view.findViewById(R.id.vp_bible);
        BibleFragmentPagerAdapter adapter = new BibleFragmentPagerAdapter(getChildFragmentManager(),
                Book.getMocked().get(0));
        viewPager.setAdapter(adapter);
        mActivity.setupTabLayoutWithViewPager(viewPager);

        return binding.getRoot();
    }

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.bible));
        ab.setBackButton(false);
        ab.setCalendarBarVisible(false);
        ab.setTabLayoutVisible(true);
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
    public class BibleFragmentPagerAdapter extends FragmentPagerAdapter implements OnBookSelectedListener{

        ChaptersFragment chaptersFragment;
        private Book mSelectedBook;

        public BibleFragmentPagerAdapter(FragmentManager fm, Book firstBook) {
            super(fm);
            mSelectedBook = firstBook;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                BooksFragment booksFragment = new BooksFragment();
                booksFragment.setOnBooksSelectedListener(this);
                return booksFragment;
            } else {
                chaptersFragment = (ChaptersFragment) ChaptersFragment.getInstance(mSelectedBook);
                return chaptersFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return getString(R.string.books);
                case 1:
                    return getString(R.string.chapters);
                default:
                    return null;
            }
        }

        public void setCurrentBook(Book book) {
            mSelectedBook = book;
            chaptersFragment.setSelectedBook(mSelectedBook);
        }
        @Override
        public void onBookSelected(Book book) {
           setCurrentBook(book);
            viewPager.setCurrentItem(1);
        }
    }


}
