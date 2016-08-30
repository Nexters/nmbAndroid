package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.URL_Define;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView img_main_box, btn_setting, btn_user, btn_write,img_new_message;
    private TextView txt_box_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        LoadFromServer();
    }

    private void initView() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        img_main_box = (ImageView) findViewById(R.id.img_main_box);
        btn_setting = (ImageView) findViewById(R.id.btn_setting);
        btn_user = (ImageView) findViewById(R.id.btn_user);
        btn_write = (ImageView) findViewById(R.id.btn_write);
        txt_box_count = (TextView) findViewById(R.id.txt_box_count);
        Glide.with(MainActivity.this)
                .load(R.drawable.box_count_zero)
                .placeholder(R.drawable.box_count_zero)
                .error(R.drawable.box_count_zero)
                .into(img_main_box);

        btn_write.setOnClickListener(this);
        img_main_box.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_user.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadFromServer();
    }

    private void LoadFromServer() {
        getReq(URL_Define.BOX_COUNT, new RequestParams(), new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.e(TAG, "onSuccess: res.toString : " + res.toString());
                try {
                    int count = res.getJSONArray("resData").getJSONObject(0).getInt("count");
                    txt_box_count.setText(count + "");
                    if (count > 0) {
                        Glide.with(MainActivity.this)
                                .load(R.drawable.box_count_not_zero)
                                .placeholder(R.drawable.box_count_not_zero)
                                .error(R.drawable.box_count_not_zero)
                                .into(img_main_box);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_main_box:
                startActivity(new Intent(MainActivity.this, MyboxActivity.class));
                break;
            case R.id.btn_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.btn_write:
                startActivity(new Intent(MainActivity.this, WriteActivity.class));
                break;
            case R.id.btn_user:
                startActivity(new Intent(MainActivity.this, MyPageActivity.class));
                break;
        }
    }

}
