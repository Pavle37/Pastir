package com.pastir.fragment;

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

import com.pastir.R;
import com.pastir.databinding.FragmentLessonOverviewBinding;
import com.pastir.databinding.FragmentPlaceholderLessonBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Lesson;
import com.pastir.model.MorningVerse;
import com.pastir.presenter.ActionBarPresenter;
import com.pastir.presenter.LessonsPresenter;
import com.pastir.storage.DataSource;

import java.util.List;

/**
 * Used to show view pager with lessons
 */

public class LessonOverviewFragment extends BaseFragment {

    private ViewPager mViewPager;
    private LessonsPresenter mPresenter;

    public LessonOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        final FragmentLessonOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_overview, container, false);

        String currentDate = getArguments().getString(ARGS_KEY);
        LessonOverviewFragment.SectionsPagerAdapter sectionsPagerAdapter = new LessonOverviewFragment.SectionsPagerAdapter(getChildFragmentManager());

        mPresenter = new LessonsPresenter();
        mPresenter.bindView(this);
        binding.setPresenter(mPresenter);

        List<Lesson> lessons = DataSource.getInstance().getLessons();
        int position = Lesson.getPositionForId(currentDate);
        Lesson currentLesson = lessons.get(position);
        binding.setLesson(currentLesson);
        mPresenter.loadVerseAudio(currentLesson);

        // Set up the ViewPager with the sections adapter.
        mViewPager = binding.getRoot().findViewById(R.id.vpLessons);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onDestroy();//Clear the MediaPlayer
                Lesson lesson = DataSource.getInstance().getLessons().get(position);
                binding.setLesson(lesson);
                mPresenter.loadVerseAudio(lesson);//Reinitialize it
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
        ab.setTitle(getString(R.string.lesson));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setMorningVersesActionBar(true);
        return ab;
    }


    public static BaseFragment getInstance(String date) {
        BaseFragment instance = new LessonOverviewFragment();
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

    public void setViewPagerItem(Lesson lesson) {
        mViewPager.setCurrentItem(Lesson.getPositionForId(lesson.getDate()));
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
        public static LessonOverviewFragment.PlaceholderFragment newInstance(int sectionNumber) {
            LessonOverviewFragment.PlaceholderFragment fragment = new LessonOverviewFragment.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FragmentPlaceholderLessonBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_placeholder_lesson, container, false);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            binding.setLesson(DataSource.getInstance().getLessons().get(sectionNumber));

            return binding.getRoot();
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
            return LessonOverviewFragment.PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return DataSource.getInstance().getLessons().size();
        }

    }


}
