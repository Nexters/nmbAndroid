package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.URL_Define;

import io.fabric.sdk.android.Fabric;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView img_main_box,btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        initView();
        LoadFromServer();
    }

    private void initView() {
        img_main_box = (ImageView) findViewById(R.id.img_main_box);
        btn_setting = (ImageView) findViewById(R.id.btn_setting);
        img_main_box = (ImageView) findViewById(R.id.img_main_box);

        Glide.with(MainActivity.this).load(R.drawable.main_box_gif).asGif().into(img_main_box);
    }

    private void LoadFromServer() {
        getReq(URL_Define.TEST_URL, new RequestParams(), new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.e(TAG, "onSuccess: res.toString : " + res.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);
            }
        });
    }

}
