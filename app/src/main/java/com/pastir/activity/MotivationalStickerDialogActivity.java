package com.pastir.activity;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.pastir.R;
import com.pastir.databinding.ActivityMotivationalStickerDialogBinding;
import com.pastir.model.MotivationalSticker;
import com.pastir.util.Utils;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class MotivationalStickerDialogActivity extends AppCompatActivity {

    private static final String ARGS_KEY = "com.creitive.motivational.sticker.activity";
    private MotivationalSticker mMotivationalSticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMotivationalStickerDialogBinding binding
        = DataBindingUtil.setContentView(this,R.layout.activity_motivational_sticker_dialog);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.95),(int)(height*0.55));

        MotivationalSticker motivationalSticker = Utils.General.deserializeFromJson(
                getIntent().getStringExtra(ARGS_KEY), MotivationalSticker.class
        );

        // the content
        binding.setMotivationalSticker(motivationalSticker);
        binding.setActivity(this);

    }

    public static void show(AppCompatActivity callerActivity,MotivationalSticker motivationalSticker){
        Intent intent = new Intent(callerActivity,MotivationalStickerDialogActivity.class);
        intent.putExtra(ARGS_KEY, Utils.General.serializeToJson(motivationalSticker));
        callerActivity.startActivity(intent);
    }

    public void dismiss(){
        this.finish();
    }

    public void onShareClicked(MotivationalSticker motivationalSticker){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                shareContent(motivationalSticker);
            } else {
                mMotivationalSticker = motivationalSticker;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 37);
            }
        } else {
            shareContent(motivationalSticker);
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
                            .with(MotivationalStickerDialogActivity.this)
                            .load(ms.getImageMain())
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                    String url = MediaStore.Images.Media.insertImage(
                            MotivationalStickerDialogActivity.this.getContentResolver(),
                            bm, "title", "description"
                    );
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                    intent.setAction(Intent.ACTION_SEND);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            {
                shareContent(mMotivationalSticker);
            }
        }
    }


}
