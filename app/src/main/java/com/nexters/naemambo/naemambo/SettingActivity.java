package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nexters.naemambo.naemambo.util.BaseActivity;

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private SwitchCompat toggle_message_on_off;
    private TextView btn_opensource, btn_tutorial, btn_developer_designer;
    private LinearLayout layout_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setActionBarConfig("Setting", 8);
        toggle_message_on_off = (SwitchCompat) findViewById(R.id.toggle_message_on_off);
        btn_opensource = (TextView) findViewById(R.id.btn_opensource);
        btn_tutorial = (TextView) findViewById(R.id.btn_tutorial);
        btn_developer_designer = (TextView) findViewById(R.id.btn_developer_designer);

        toggle_message_on_off.setOnCheckedChangeListener(this);
        btn_opensource.setOnClickListener(this);
        btn_developer_designer.setOnClickListener(this);


    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        layout_root = (LinearLayout) findViewById(R.id.layout_root);

        Glide.with(this).load(R.drawable.background_setting).asBitmap().into(new SimpleTarget<Bitmap>(layout_root.getWidth(), layout_root.getHeight()) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layout_root.setBackground(drawable);
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.toggle_message_on_off:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_opensource:
                startActivity(new Intent(SettingActivity.this, OpenSourceActivity.class));
                break;
            case R.id.btn_developer_designer:
                startActivity(new Intent(SettingActivity.this, CreditActivity.class));
                break;
        }
    }
}
