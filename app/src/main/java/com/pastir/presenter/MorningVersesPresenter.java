package com.pastir.presenter;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.pastir.R;
import com.pastir.fragment.DatePickerFragment;
import com.pastir.fragment.MorningVerseOverviewFragment;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.model.ListItem;
import com.pastir.model.MorningVerse;
import com.pastir.model.OnListItemClickListener;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;

import java.util.List;

/**
 * Used to handle interactions with the morning verses fragment
 */
public class MorningVersesPresenter extends ActionBarPresenter<MorningVersesFragment> implements OnListItemClickListener, DatePickerDialog.OnDateSetListener {

    private boolean onDateSetCalled;

    @Override
    public void onItemClicked(ListItem item) {
        getView().loadFragment(MorningVerseOverviewFragment.getInstance(((MorningVerse) item).getId()));
    }

    public void loadData() {
        if (mDataSource.getMorningVerses() != null)
            onMorningVersesLoaded(mDataSource.getMorningVerses());
        //TODO: Create call to WebController to fetch the data
    }

    private void onMorningVersesLoaded(List<MorningVerse> morningVerses) {
        getView().setAdapter(morningVerses, this);
    }

    @Override
    public void openCalendar() {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(this);
        fragment.show(getView().getFragmentManager(), "tag");
    }

    @Override
    public void openCloud() {
        Utils.SingleToast.show(getContext(), R.string.not_implemented);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!onDateSetCalled) {
            month++;
            String date = dayOfMonth + "." + (month < 10 ? "0" + month : "" + month) + "." + year;
            for (MorningVerse morningVerse : DataSource.getInstance().getMorningVerses()) {
                if (morningVerse.getDate().equals(date)) {
                    getView().loadFragment(MorningVerseOverviewFragment.getInstance(morningVerse.getId()));
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
