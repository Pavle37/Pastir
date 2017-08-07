package com.pastir.fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.pastir.R;
import com.pastir.databinding.FragmentDialogMotivationalStickerBinding;
import com.pastir.model.HomeListItem;
import com.pastir.model.MotivationalSticker;
import com.pastir.util.Utils;

/**
 * Created by Uros on 8/5/2017.
 */

public class MotivationalStickerDialog extends DialogFragment {

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
        FragmentDialogMotivationalStickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_motivational_sticker, null, true);
        MotivationalSticker motivationalSticker = Utils.General.deserializeFromJson(getArguments().getString(ARGS_KEY), MotivationalSticker.class);


        binding.setMotivationalSticker(motivationalSticker);

        builder.setView(binding.getRoot());

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static MotivationalStickerDialog getInstance(MotivationalSticker motivationalSticker) {
        MotivationalStickerDialog instance = new MotivationalStickerDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(motivationalSticker));
        instance.setArguments(args);
        return instance;
    }
}
