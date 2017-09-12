package com.pastir.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pastir.R;
import com.pastir.util.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.General.fullScreenActivity(this);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME);
    }
}
