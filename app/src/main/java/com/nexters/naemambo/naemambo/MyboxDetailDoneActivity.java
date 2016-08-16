package com.nexters.naemambo.naemambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nexters.naemambo.naemambo.util.BaseActivity;

public class MyboxDetailDoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_done);
        setActionBarConfig("전한경 님의 메세지", 8);
    }
}
