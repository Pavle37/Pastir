package com.pastir.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import com.pastir.R;
import com.pastir.activity.MainActivity;
import com.pastir.util.Utils;

/**
 * Dialog used to inform user about Internet connection issues
 */

public class NoInternetConnectionDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.no_internet_connection))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.no_internet_connection_message)
                .setPositiveButton(R.string.check_again,(x,y)->{
                    //Ignore this here, do it in onResume
                })
                .setNegativeButton(R.string.exit,(x,y )-> getActivity().finish())
                .setCancelable(false)
                .create();


        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        /*First we get the dialog, set the text color to it and override the click so it doesn't dismiss*/
        AlertDialog dialog = (AlertDialog) getDialog();
        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(getResources().getColor(R.color.colorPrimary));
        positive.setOnClickListener(view -> {
            if(Utils.General.hasInternetConnection(getActivity())){
                ((MainActivity)getActivity()).loadStartingFragment();
                dismiss();
            }
        });
        /*Then we change the color of the second button*/
        Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negative.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
