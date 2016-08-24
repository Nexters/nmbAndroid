package com.nexters.naemambo.naemambo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.SPreference;
import com.nexters.naemambo.naemambo.util.URL_Define;
import com.wdullaer.materialdatetimepicker.date.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class WriteActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView btn_save_box, btn_direct_send, action_bar_write_title,
            dateTextView;//임시 TextView
    private LinearLayout layout_root;
    private RelativeLayout btn_add_friends, btn_choice_date;
    private Dialog dialog_save_or_send;
    private EditText edit_content, edit_title;
    private ActionBar actionBar;
    private ImageView btn_actionbar_goto_list, btn_actionbar_send, btn_actionbar_back;
    private SPreference pref;
    private static final String TAG = WriteActivity.class.getSimpleName();
    String targetDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
    }

    private void initView() {
        pref = new SPreference(WriteActivity.this);
        layout_root = (LinearLayout) findViewById(R.id.layout_root);
        btn_add_friends = (RelativeLayout) findViewById(R.id.btn_add_friends);
        btn_choice_date = (RelativeLayout) findViewById(R.id.btn_choice_date);
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_content = (EditText) findViewById(R.id.edit_content);

        //보내기 다이얼로그
        dialog_save_or_send = new Dialog(WriteActivity.this, R.style.dialogStyle);
        dialog_save_or_send.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_save_or_send.setContentView(R.layout.dialog_send_or_save);
        btn_save_box = (TextView) dialog_save_or_send.findViewById(R.id.btn_save_box);
        btn_direct_send = (TextView) dialog_save_or_send.findViewById(R.id.btn_direct_send);
        //임시 텍스트 For PickerDATE
        dateTextView = (TextView) findViewById(R.id.date_text);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(8);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_send_or_save_layout);
            btn_actionbar_goto_list = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_goto_list);
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
        btn_actionbar_goto_list.setOnClickListener(this);
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
                startActivityForResult(new Intent(WriteActivity.this, FriendListActivity.class), Const.INTENT_FRIENDS_LIST_CODE);
                break;
            case R.id.btn_choice_date:
                //날짜선택 화면
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        WriteActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.btn_direct_send:
                //서버에 바로전송 하면서 바로 상대방에게도
                sendContent(true);
                dialog_save_or_send.dismiss();
                break;
            case R.id.btn_save_box:
                //서버에 저장만..
                sendContent(false);
                dialog_save_or_send.dismiss();
                break;
            case R.id.btn_actionbar_send:
                dialog_save_or_send.show();
                break;
            case R.id.btn_actionbar_goto_list:
                startActivity(new Intent(WriteActivity.this, MyboxActivity.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d(TAG, "onActivityResult() called with: " + "requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
            Log.e(TAG, "onActivityResult: " + data.getData().toString());
        }
        if (requestCode == RESULT_OK) {
            if (resultCode == Const.INTENT_FRIENDS_LIST_CODE) {

            }
        }
    }

    /**
     * 서운한마음 쓴 내용 서버로 전송
     * 자바 코드 양 너무많아...
     */
    private void sendContent(boolean isDirect) {
        RequestParams params = new RequestParams();
        if (isDirect) {//친구한테 바로 보낼때
            params.put("status", 1);
//                            params.put("shuserid",)//친구 아이디 넣어야행
        } else {//서버에만 저장할때
            params.put("status", 0);
        }
        if (!TextUtils.isEmpty(edit_title.getText().toString())) {
            params.put("title", edit_title.getText().toString());
        } else if (targetDate.isEmpty()) {
            Toast.makeText(WriteActivity.this, "서운했던 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }else if(edit_content.getText().toString().isEmpty()){
            Toast.makeText(WriteActivity.this, "무엇이 서운했나요? 내용을 적어주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("content", edit_content.getText().toString());
        params.put("target", targetDate);
        Log.e(TAG, "sendContent: " + params.toString());
        postReq(URL_Define.WRITE, params, new ConnHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.e(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], res = [" + res + "]");

                try {
                    if (statusCode == 200 && res.getString("result").equals("success")) {
                        startActivity(new Intent(WriteActivity.this, MyboxActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
                try {
                    if (statusCode == 200 || res.getString("result").equals("success")) {
                        startActivity(new Intent(WriteActivity.this, MyboxActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], t = [" + t + "], res = [" + res + "]");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);

                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], t = [" + t + "]");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //String date = "You picked the following date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        String month = Integer.toString(++monthOfYear),
                day = Integer.toString(dayOfMonth);

        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        }
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        }
        targetDate = year + month + day;
        String date = year + "-"
                + month + "-"
                + day;

        dateTextView.setText(date);
    }


}
