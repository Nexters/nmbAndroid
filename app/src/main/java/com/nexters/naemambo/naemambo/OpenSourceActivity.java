package com.nexters.naemambo.naemambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
    }
}
