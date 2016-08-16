package com.nexters.naemambo.naemambo;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.URL_Define;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WriteActivity extends BaseActivity implements View.OnClickListener {

    private TextView btn_save_box, btn_direct_send, action_bar_write_title;
    private LinearLayout layout_root;
    private RelativeLayout btn_add_friends, btn_choice_date;
    private Dialog dialog_save_or_send;
    private EditText edit_content;
    private ActionBar actionBar;
    private ImageView btn_actionbar_box, btn_actionbar_send, btn_actionbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
    }

    private void initView() {
        layout_root = (LinearLayout) findViewById(R.id.layout_root);
        btn_add_friends = (RelativeLayout) findViewById(R.id.btn_add_friends);
        btn_choice_date = (RelativeLayout) findViewById(R.id.btn_choice_date);
        edit_content = (EditText) findViewById(R.id.edit_content);

        //보내기 다이얼로그
        dialog_save_or_send = new Dialog(WriteActivity.this, R.style.dialogStyle);
        dialog_save_or_send.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_save_or_send.setContentView(R.layout.dialog_send_or_save);
        btn_save_box = (TextView) dialog_save_or_send.findViewById(R.id.btn_save_box);
        btn_direct_send = (TextView) dialog_save_or_send.findViewById(R.id.btn_direct_send);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(8);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_send_or_save_layout);
            btn_actionbar_box = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_box);
            action_bar_write_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_write_title);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            btn_actionbar_send = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_send);
        }
        action_bar_write_title.setText("풀어내기");
        btn_actionbar_back.setOnClickListener(this);
        btn_add_friends.setOnClickListener(this);
        btn_choice_date.setOnClickListener(this);
        btn_direct_send.setOnClickListener(this);
        btn_save_box.setOnClickListener(this);
        btn_actionbar_send.setOnClickListener(this);
        btn_actionbar_box.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_actionbar_back:
                finish();
                break;
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
            case R.id.btn_actionbar_send:
                dialog_save_or_send.show();
                break;
            case R.id.btn_actionbar_box:
                break;

        }
    }

    /**
     * 서운한마음 쓴 내용 서버로 전송
     */
    private void sendContent(boolean isDirect) {
        JSONObject object = new JSONObject();
        try {
            //object.put(key,vlaue);





            object.put("content",edit_content.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
