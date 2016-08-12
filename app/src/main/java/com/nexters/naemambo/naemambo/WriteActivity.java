package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.URL_Define;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WriteActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btn_choice_date, btn_add_friends, btn_save_box, btn_direct_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();

    }

    private void initView() {
        btn_add_friends = (ImageView) findViewById(R.id.btn_add_friends);
        btn_choice_date = (ImageView) findViewById(R.id.btn_choice_date);
        btn_direct_send = (ImageView) findViewById(R.id.btn_direct_send);
        btn_save_box = (ImageView) findViewById(R.id.btn_save_box);

        btn_add_friends.setOnClickListener(this);
        btn_choice_date.setOnClickListener(this);
        btn_direct_send.setOnClickListener(this);
        btn_save_box.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_friends:
                //친구목록 선택으로...
                break;
            case R.id.btn_choice_date:
                //날짜선택 화면
                break;
            case R.id.btn_direct_send:
                //서버에 바로전송 하면서 바로 상대방에게도
                sendContent(true);
                break;
            case R.id.btn_save_box:
                //서버에 저장만..
                sendContent(false);
                break;

        }
    }

    /**
     * 서운한마음 쓴 내용 서버로 전송
     */
    private void sendContent(boolean isDirect) {
        JSONObject object = new JSONObject();
        postReq(URL_Define.BASE_URL, object, new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
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
