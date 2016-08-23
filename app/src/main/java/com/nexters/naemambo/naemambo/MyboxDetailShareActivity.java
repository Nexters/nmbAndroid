package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.listItem.MessageItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

public class MyboxDetailShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView btn_complete, text_subject, text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_share);
        setActionBarConfig("전한경 님의 메세지", 8);
        btn_complete = (TextView) findViewById(R.id.btn_complete);

        Intent intent = getIntent();
        text_subject = (TextView) findViewById(R.id.text_subject);
        text_content = (TextView) findViewById(R.id.text_content);

        MessageItem item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_DONE);
        text_content.setText(item.content);
        text_subject.setText(item.subject);

        btn_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_complete:
                break;
        }
    }
}
