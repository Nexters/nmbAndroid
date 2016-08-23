package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.listItem.MessageItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

public class MyboxDetailDoneActivity extends BaseActivity {

    private TextView text_subject,text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_done);
        setActionBarConfig("전한경 님의 메세지", 8);
        Intent intent = getIntent();
        text_subject = (TextView) findViewById(R.id.text_subject);
        text_content = (TextView) findViewById(R.id.text_content);

        MessageItem item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_DONE);
        text_content.setText(item.content);
        text_subject.setText(item.subject);
    }
}
