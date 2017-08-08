package com.pastir.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.pastir.R;
import com.pastir.databinding.FragmentDialogMotivationalStickerBinding;
import com.pastir.model.MotivationalSticker;
import com.pastir.util.Utils;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Shows single motivational sticker
 */

public class MotivationalStickerDialog extends DialogFragment {

    public static final String ARGS_KEY = "com.creitive.craftmaster.bundle";
    private MotivationalSticker mMotivationalSticker;
    private BaseFragment mParent;

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
        binding.setDialog(this);

        builder.setView(binding.getRoot());

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void onShareClicked(final MotivationalSticker ms) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                shareContent(ms);
            } else {
                mMotivationalSticker = ms;
                mParent.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 37);
            }
        } else {
            shareContent(ms);
        }
    }

    private void shareContent(final MotivationalSticker ms) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = null;
                Intent intent = new Intent();
                try {
                    file = Glide
                            .with(getContext())
                            .load(ms.getImageUrl())
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                    String url = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm, "title", "description");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent, "Share Image"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String filepath = file.getAbsolutePath();
                Log.d("FilePath", filepath);
            }
        }).start();
    }

    public void onRequestPermissionsGranted() {
           shareContent(mMotivationalSticker);
    }

    public static MotivationalStickerDialog getInstance(MotivationalSticker motivationalSticker) {
        MotivationalStickerDialog instance = new MotivationalStickerDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY, Utils.General.serializeToJson(motivationalSticker));
        instance.setArguments(args);
        return instance;
    }


    public void setParent(BaseFragment parent) {
        mParent = parent;
    }
}
