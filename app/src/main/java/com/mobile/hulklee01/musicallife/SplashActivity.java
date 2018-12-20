package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            mContext = this;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, FeederActivity.class);
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    startActivity(intent);
                    finish();
                }

            }, 2000);

        }
    }

