package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), FeederActivity.class);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                startActivity(intent);
                finish();
            }, 2000);

        }
    }

