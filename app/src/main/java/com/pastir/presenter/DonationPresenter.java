package com.pastir.presenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import com.pastir.R;
import com.pastir.fragment.DonationFragment;
import com.pastir.util.Utils;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Handles all activities related to donations
 */

public class DonationPresenter extends ActionBarPresenter<DonationFragment> {

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + getString(R.string.donation_email));
        intent.setData(data);
        getView().mActivity.startActivity(intent);
    }

    public void callPhone() {
        try {
            if (isTelephonyEnabled()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getString(R.string.donation_phone_number)));
                getView().mActivity.startActivity(intent);
            } else Utils.SingleToast.show(getContext(), getString(R.string.telepfony_not_enabled));
        } catch (ActivityNotFoundException e) {
            Utils.SingleToast.show(getContext(), getString(R.string.telepfony_not_enabled));
        }
    }

    private boolean isTelephonyEnabled() {
        TelephonyManager telephonyManager = (TelephonyManager) getView().mActivity.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager != null && telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
}
