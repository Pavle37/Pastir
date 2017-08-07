package com.pastir.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.pastir.R;
import com.pastir.databinding.FragmentDialogCloudBinding;
import com.pastir.model.Cloud;
import com.pastir.util.Utils;

/**
 * Dialog used to show sunset and sunrise
 */

public class CloudDialog extends DialogFragment{
    public static final String ARGS_KEY = "com.creitive.craftmaster.bundle";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        FragmentDialogCloudBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_cloud, null, true);
        Cloud cloud = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), Cloud.class);

        binding.setCloud(cloud);

        builder.setView(binding.getRoot());

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static CloudDialog getInstance(Cloud cloud) {
        CloudDialog instance = new CloudDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(cloud));
        instance.setArguments(args);
        return instance;
    }

}
