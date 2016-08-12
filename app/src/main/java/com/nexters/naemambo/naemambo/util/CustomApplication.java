package com.nexters.naemambo.naemambo.util;

import android.app.Application;
import android.os.Bundle;

import com.nexters.naemambo.naemambo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jjgod on 2016-08-12.
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("SDMiSaeng.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

