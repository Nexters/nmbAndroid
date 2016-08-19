package com.nexters.naemambo.naemambo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.util.BaseActivity;

public class MyboxDetailGeneralActivity extends BaseActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private ImageView btn_actionbar_back, btn_actionbar_delete;
    private TextView action_bar_write_title, text_share_subject, text_share_content, btn_delete_yes, btn_delete_no;
    private Dialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_general);

        initView(getIntent());

    }

    private void initView(Intent intent) {
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

            action_bar_write_title.setText("전한경의 메세지");
            btn_actionbar_back.setOnClickListener(this);
            btn_actionbar_delete.setOnClickListener(this);
        }


        text_share_subject = (TextView) findViewById(R.id.text_share_subject);
        text_share_content = (TextView) findViewById(R.id.text_share_content);
        text_share_subject.setText("tesetsubject");
        text_share_content.setText("tesetcontest 끝내고싶어");

        deleteDialog = new Dialog(MyboxDetailGeneralActivity.this, R.style.dialogStyle);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        deleteDialog.setContentView(R.layout.dialog_delete_yes_or_no);
        btn_delete_no = (TextView) deleteDialog.findViewById(R.id.btn_delete_no);
        btn_delete_yes = (TextView) deleteDialog.findViewById(R.id.btn_delete_yes);



        /*     dialog_save_or_send = new Dialog(WriteActivity.this, R.style.dialogStyle);
        dialog_save_or_send.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_save_or_send.setContentView(R.layout.dialog_send_or_save);
        * */
        btn_delete_yes.setOnClickListener(this);
        btn_delete_no.setOnClickListener(this);
        text_share_subject.setOnClickListener(this);
        text_share_content.setOnClickListener(this);
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
            case R.id.text_share_subject:
                text_share_subject.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.text_share_content:
                text_share_content.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.btn_delete_yes:
                deleteBox();
                break;
            case R.id.btn_delete_no:
                deleteDialog.dismiss();
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (text_share_subject != null && text_share_content != null) {
            text_share_subject.setOnClickListener(null);
            text_share_content.setOnClickListener(null);
        }
    }
}
