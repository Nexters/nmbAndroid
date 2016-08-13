package com.nexters.naemambo.naemambo.util;

import android.app.Application;
import android.os.Bundle;

import com.nexters.naemambo.naemambo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jjgod on 2016-08-12.
 * 커스텀 폰트 적용
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("SDMiSaeng.ttf")//asset 폴더
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

