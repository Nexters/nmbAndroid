package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.URL_Define;

import io.fabric.sdk.android.Fabric;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView img_main_box, btn_setting, btn_user, btn_write;
    private TextView txt_box_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. FriendListActivity에서 전역변수에 넣으면서 이것저것해봤는데 안되서 함수밖으로 나오질 않아서 ...
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        initView();
        LoadFromServer();
        //2. 결국은 메인에서 intent로 넘겨서 FriendListActivity 왔더니 되가지고 버튼누르면 메인엑티비티에있는 값들 리스트에서 불러오게 했습니다.ㅠㅠㅠ
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
                .load(R.drawable.main_mybox)
                .asGif()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
                    txt_box_count.setText(res.getJSONArray("resData").getJSONObject(0).getString("count"));
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
