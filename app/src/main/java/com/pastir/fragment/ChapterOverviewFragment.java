package com.pastir.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pastir.R;
import com.pastir.databinding.FragmentChapterOverviewBinding;
import com.pastir.databinding.FragmentPlaceholderChapterBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Chapter;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.ChapterOverviewPresenter;
import com.pastir.storage.DataSource;

public class ChapterOverviewFragment extends BaseFragment {

    public static final String ARGS_KEY_BOOK = "com.pastir.bundle_book";
    public static final String ARGS_KEY_SELECTED_CHAPTER = "com.pastir.bundle_selected_chapter";

    private ChapterOverviewPresenter mPresenter;
    private FragmentChapterOverviewBinding mBinding;
    private ViewPager mViewPager;

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
         mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapter_overview, container, false);

        int selectedBook = getArguments().getInt(ARGS_KEY_BOOK);
        int selectedChapter = getArguments().getInt(ARGS_KEY_SELECTED_CHAPTER);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),
                selectedBook, selectedChapter);

        mPresenter = new ChapterOverviewPresenter();
        mPresenter.bindView(this);
        mBinding.setPresenter(mPresenter);
        Chapter chapter = DataSource.getInstance().getBible().get(selectedBook).getChapters().get(selectedChapter);
        mBinding.setChapter(chapter);
        mPresenter.loadChapterAudio(chapter);

        // Set up the ViewPager with the sections adapter.
        mViewPager = mBinding.getRoot().findViewById(R.id.vpChapters);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(selectedChapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onDestroy();//Clear the MediaPlayer
                Chapter chapter = DataSource.getInstance().getBible().get(selectedBook)
                        .getChapters().get(position);
                mBinding.setChapter(chapter);
                mPresenter.loadChapterAudio(chapter);//Reinitialize it

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return mBinding.getRoot();
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

    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.bible));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setCalendarBarVisible(false);
        ab.setTabLayoutVisible(false);
        return ab;
    }

    public static BaseFragment getInstance(int selectedBook, int selectedChapter) {
        BaseFragment instance = new ChapterOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY_BOOK, selectedBook);
        args.putInt(ARGS_KEY_SELECTED_CHAPTER, selectedChapter);
        instance.setArguments(args);
        return instance;
    }

    public ActionBarPresenter getHomePresenter() {
        return mPresenter;
    }

    public void scrollViewPagerRight() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void scrollViewPagerLeft() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_CHAPTER_POSITION = "chapter_position";
        private static final String ARG_BOOK_POSITION = "book_position";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int bookPosition, int chapterPosition) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_BOOK_POSITION, bookPosition);
            args.putInt(ARG_CHAPTER_POSITION, chapterPosition);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FragmentPlaceholderChapterBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_placeholder_chapter, container, false);

            int bookPosition = getArguments().getInt(ARG_BOOK_POSITION);
            int chapterPosition = getArguments().getInt(ARG_CHAPTER_POSITION);
            Chapter chapter = DataSource.getInstance().getBible().get(bookPosition).getChapters().get(chapterPosition);
            binding.setChapter(chapter);

            setOnLongViewPressListener(binding.getRoot(), chapter);
            return binding.getRoot();
        }

        private void setOnLongViewPressListener(View root, Chapter chapter) {
            LinearLayout llChapterOverview = root.findViewById(R.id.llChapterOverview);
            TextView tvChapterText = root.findViewById(R.id.tvChapterText);
            llChapterOverview.setOnLongClickListener(view -> {

                String shareBody = tvChapterText.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, chapter.getText());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Chapter"));
                return true;
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private int chapterPosition;
        private int bookPosition;

        public SectionsPagerAdapter(FragmentManager fm, int selectedBook, int chapterPosition) {
            super(fm);
            this.chapterPosition = chapterPosition;
            this.bookPosition = selectedBook;
        }

        public void setChapterPosition(int chapterPosition) {
            this.chapterPosition = chapterPosition;
            notifyDataSetChanged();
        }

        public int getChapterPosition() {
            return chapterPosition;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(bookPosition, chapterPosition);
        }

        @Override
        public int getCount() {
            return DataSource.getInstance().getBible().get(bookPosition).getChapters().size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
