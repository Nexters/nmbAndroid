package com.nexters.naemambo.naemambo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nexters.naemambo.naemambo.util.BaseActivity;

public class CreditActivity extends BaseActivity {

    private LinearLayout layout_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        setActionBarConfig("Setting", 8);
        ImageView logo = (ImageView) findViewById(R.id.credit_logo);
        layout_root = (LinearLayout) findViewById(R.id.layout_root);

        Glide.with(CreditActivity.this).load(R.drawable.login_logo).into(logo);
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
}
