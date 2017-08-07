package com.pastir.presenter;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.pastir.R;
import com.pastir.fragment.DatePickerFragment;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.model.MorningVerse;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;

/**
 * Used to handle interactions with the morning verse overview fragment
 */

public class MorningVerseOverviewPresenter extends ActionBarPresenter<MorningVerseOverviewFragment> implements DatePickerDialog.OnDateSetListener {

    private boolean onDateSetCalled;

    @Override
    public void onBackPressed() {
        getView().mActivity.onBackPressed();
    }

    public void onPlayClicked() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    public void onLeftArrowClicked() {
        getView().scrollViewPagerLeft();
    }

    public void onRightArrowClicked() {
        getView().scrollViewPagerRight();
    }

    @Override
    public void openCloud() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    @Override
    public void openCalendar() {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(this);
        fragment.show(getView().getFragmentManager(), "tag");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!onDateSetCalled) {
            month++;
            String date = dayOfMonth + "." + (month < 10 ? "0" + month : "" + month) + "." + year;
            for (MorningVerse morningVerse : DataSource.getInstance().getMorningVerses()) {
                if (morningVerse.getDate().equals(date)) {
                    getView().setViewPagerItem(morningVerse);

                    onDateSetCalled = true;
                    break;
                }
            }
            if (!onDateSetCalled) {
                Utils.SingleToast.show(getContext(), R.string.no_morning_verse);
            }
        } else onDateSetCalled = false;
    }
}
