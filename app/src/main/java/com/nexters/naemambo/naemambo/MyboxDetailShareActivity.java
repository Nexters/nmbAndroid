package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.util.BaseActivity;

public class MyboxDetailShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView btn_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_share);
        setActionBarConfig("전한경 님의 메세지", 8);
        btn_complete = (TextView) findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_complete:
                break;
        }
    }
}
