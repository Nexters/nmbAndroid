package com.nexters.naemambo.naemambo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.listItem.MessageItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.URL_Define;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyboxDetailGeneralActivity extends BaseActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private ImageView btn_actionbar_back, btn_actionbar_delete;
    private TextView action_bar_write_title, btn_delete_yes, btn_delete_no, text_target_date, btn_detail_send_box;
    private Dialog deleteDialog;
    private MessageItem item;
    private static final String TAG = MyboxDetailGeneralActivity.class.getSimpleName();
    private long backKeyPressedTime = 0;
    private Toast toast;
    private EditText edit_share_content, edit_share_subject;
    private String fromTo = "To ";
    private Dialog dialog_hope_msg;
    private TextView btn_hope_msg, txt_msg_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_general);
        Intent intent = getIntent();
        item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_GENERAL);

        Log.e(TAG, "onCreate: kjdklfjdkl");
        initView(item);
    }

    private void initView(MessageItem item) {
        //actionbar custom
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(0);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_direct_send_layout);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            action_bar_write_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_write_title);
            btn_actionbar_delete = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_delete);

            action_bar_write_title.setText(fromTo + item.name);
            btn_actionbar_back.setOnClickListener(this);
            btn_actionbar_delete.setOnClickListener(this);
        }

        dialog_hope_msg = new Dialog(MyboxDetailGeneralActivity.this, R.style.dialogStyle);
        dialog_hope_msg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_hope_msg.setContentView(R.layout.dialog_hope_msg);
        txt_msg_content = (TextView) dialog_hope_msg.findViewById(R.id.txt_msg_content);
        btn_hope_msg = (TextView) dialog_hope_msg.findViewById(R.id.btn_hope_msg);

        edit_share_subject = (EditText) findViewById(R.id.edit_share_subject);
        edit_share_content = (EditText) findViewById(R.id.edit_share_content);
        text_target_date = (TextView) findViewById(R.id.text_target_date);
        btn_detail_send_box = (TextView) findViewById(R.id.btn_detail_send_box);

        edit_share_subject.setText(item.subject);
        edit_share_content.setText(item.content);
        text_target_date.setText(item.date);
        txt_msg_content.setText("서운한 티내볼까요?");
        btn_hope_msg.setText("네, 하고싶어요");

        deleteDialog = new Dialog(MyboxDetailGeneralActivity.this, R.style.dialogStyle);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        deleteDialog.setContentView(R.layout.dialog_delete_yes_or_no);
        btn_delete_no = (TextView) deleteDialog.findViewById(R.id.btn_delete_no);
        btn_delete_yes = (TextView) deleteDialog.findViewById(R.id.btn_delete_yes);

        /*     dialog_save_or_send = new Dialog(WriteActivity.this, R.style.dialogStyle);
        dialog_save_or_send.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_save_or_send.setContentView(R.layout.dialog_send_or_save);
        * */
        btn_hope_msg.setOnClickListener(this);
        btn_delete_no.setOnClickListener(this);
        btn_delete_yes.setOnClickListener(this);
        edit_share_subject.setOnClickListener(this);
        edit_share_content.setOnClickListener(this);
        btn_detail_send_box.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_actionbar_back:
                finish();
                break;
            case R.id.btn_actionbar_delete:
                deleteDialog.show();
                break;
            case R.id.edit_share_subject:
                edit_share_subject.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.edit_share_content:
                edit_share_content.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.btn_delete_yes:
                deleteBox(item.boxNo);
                deleteDialog.dismiss();
                finish();
                break;
            case R.id.btn_delete_no:
                deleteDialog.dismiss();
                break;
            case R.id.btn_detail_send_box:
                dialog_hope_msg.show();
                break;
            case R.id.btn_hope_msg:
                dialog_hope_msg.dismiss();
                updateBoxStatus(1, item.boxNo);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (edit_share_subject != null && edit_share_content != null) {
            edit_share_subject.setOnClickListener(null);
            edit_share_content.setOnClickListener(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //서버에 저장하며 종료
            updateContent(edit_share_subject.getText().toString(), edit_share_content.getText().toString(), item.boxNo);
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(this,
                "\'뒤로\'버튼을 한번 더 누르시면 임시저장되며 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void updateContent(String title, String content, int boxId) {
        RequestParams params = new RequestParams();
        if (content.isEmpty()) {
            Toast.makeText(getApplicationContext(), "무엇이 서운했나요? 내용을 적어주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("title", title);
        params.put("content", content);
        params.put("boxno", boxId);
        params.put("target", item.target);
        params.put("shuserid", item.shuserid);//친구 아이디 넣어야행
        params.put("label", item.name);//친구이름
        Log.e(TAG, "updateContent: " + params.toString());
        postReq(URL_Define.UPDATE_BOX, params, new ConnHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.e(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "],  res = [" + res + "]");
                if (statusCode == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
                finish();
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "],");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);
                finish();
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], ");
            }
        });

    }
}
