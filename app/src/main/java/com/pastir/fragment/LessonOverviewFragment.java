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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pastir.R;
import com.pastir.databinding.FragmentLessonOverviewBinding;
import com.pastir.databinding.FragmentPlaceholderLessonBinding;
import com.pastir.model.ActionBar;
import com.pastir.model.Lesson;
import com.pastir.model.SubLesson;
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
    private FragmentLessonOverviewBinding mBinding;

    public LessonOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    protected View init(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_overview, container, false);

        String fromDate = getArguments().getString(ARGS_KEY);
        int position = Lesson.getPositionForId(fromDate);

        LessonOverviewFragment.SectionsPagerAdapter sectionsPagerAdapter = new LessonOverviewFragment.SectionsPagerAdapter(getChildFragmentManager(), position);

        mPresenter = new LessonsPresenter();
        mPresenter.bindView(this);
        mBinding.setPresenter(mPresenter);
        List<Lesson> lessons = DataSource.getInstance().getLessons();

        Lesson currentLesson = lessons.get(position);
        //Load first subLesson from lesson
        SubLesson firstSubLessonInLesson = currentLesson.getSubLessons().get(0);
        mBinding.setLesson(firstSubLessonInLesson);
        mPresenter.loadSubLessonAudio(firstSubLessonInLesson);

        // Set up the ViewPager with the sections adapter.
        mViewPager = mBinding.getRoot().findViewById(R.id.vpLessons);
        mViewPager.setAdapter(sectionsPagerAdapter);
        //When lesson is opened first subLesson is shown
        mViewPager.setCurrentItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onDestroy();//Clear the MediaPlayer
                Lesson viewPagerLesson = DataSource.getInstance().getLessons()
                        .get(((LessonOverviewFragment.SectionsPagerAdapter) mViewPager.getAdapter()).getLessonPosition());
                SubLesson subLesson = viewPagerLesson.getSubLessons().get(position);
                mBinding.setLesson(subLesson);
                mPresenter.loadSubLessonAudio(subLesson);//Reinitialize it
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return mBinding.getRoot();
    }


    @Override
    protected ActionBar setActionBar() {
        ActionBar ab = super.setActionBar();
        ab.setTitle(getString(R.string.lesson));
        ab.setLeftImage(0);
        ab.setBackButton(true);
        ab.setCalendarBarVisible(true);
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

    public void setViewPagerItem(Lesson lesson, SubLesson subLesson) {
        ((LessonOverviewFragment.SectionsPagerAdapter) mViewPager.getAdapter())
                .setLessonPosition(Lesson.getPositionForId(lesson.getFromDate()));
        mViewPager.setCurrentItem(SubLesson.getPositionForId(lesson, subLesson.getDate()));
        mBinding.setLesson(subLesson);
        mPresenter.loadSubLessonAudio(subLesson);//Reinitialize it

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
        private static final String ARG_LESSON_POSITION = "lesson_position";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static LessonOverviewFragment.PlaceholderFragment newInstance(int lessonPosition, int sectionNumber) {
            LessonOverviewFragment.PlaceholderFragment fragment = new LessonOverviewFragment.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_LESSON_POSITION, lessonPosition);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FragmentPlaceholderLessonBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_placeholder_lesson, container, false);

            int lessonPosition = getArguments().getInt(ARG_LESSON_POSITION);
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            SubLesson subLesson = DataSource.getInstance().getLessons().get(lessonPosition).getSubLessons().get(sectionNumber);
            binding.setLesson(subLesson);


            setOnLongViewPressListener(binding.getRoot(), subLesson);
            return binding.getRoot();
        }

        private void setOnLongViewPressListener(View root, SubLesson subLesson) {
            LinearLayout llSubLessonOverview = root.findViewById(R.id.llSubLessonOverview);
            TextView tvSubLessonText = root.findViewById(R.id.tvSubLessonText);
            llSubLessonOverview.setOnLongClickListener(view -> {

                String shareBody = tvSubLessonText.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subLesson.getTitle());
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private int lessonPosition;

        public SectionsPagerAdapter(FragmentManager fm, int lessonPosition) {
            super(fm);
            this.lessonPosition = lessonPosition;
        }

        public void setLessonPosition(int lessonPosition) {
            this.lessonPosition = lessonPosition;
            notifyDataSetChanged();
        }

        public int getLessonPosition() {
            return lessonPosition;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return LessonOverviewFragment.PlaceholderFragment.newInstance(lessonPosition, position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return DataSource.getInstance().getLessons().get(lessonPosition).getSubLessons().size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
