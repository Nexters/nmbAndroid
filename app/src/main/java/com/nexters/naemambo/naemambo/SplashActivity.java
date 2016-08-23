package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.SPreference;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1500;
    private SPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new SPreference(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pref.getBool(Const.IS_TUTORIAL,true)){
                    //앱이 처음이면 튜토리얼
                    startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
                }else {
                    //아니면
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, SPLASH_TIME);
    }
}
