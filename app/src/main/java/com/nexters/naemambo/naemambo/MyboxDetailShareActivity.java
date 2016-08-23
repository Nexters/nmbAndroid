package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nexters.naemambo.naemambo.listItem.MessageItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class MyboxDetailShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView btn_complete, text_share_subject, text_share_content;
    private static final String TAG = MyboxDetailShareActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_share);
        btn_complete = (TextView) findViewById(R.id.btn_complete);

        Intent intent = getIntent();
        text_share_subject = (TextView) findViewById(R.id.text_share_subject);
        text_share_content = (TextView) findViewById(R.id.text_share_content);

        MessageItem item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_SHARE);
        text_share_content.setText(item.content);
        if(item.content!=null){
            text_share_subject.setText(item.subject);
        }else {
            text_share_subject.setVisibility(View.GONE);
        }
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + item.shuserid,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, "onCompleted: " + response.toString());
                        JSONObject resJson = response.getJSONObject();
                        try {
                            setActionBarConfig(resJson.isNull("name") ? "누군가로부터.." : resJson.getString("name") + "부터..", 8);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

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
