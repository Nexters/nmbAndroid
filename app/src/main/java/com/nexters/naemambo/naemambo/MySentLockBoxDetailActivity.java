package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MySentLockBoxDetailActivity extends BaseActivity {

    private TextView text_subject, text_content;
    private static final String TAG = MySentLockBoxDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sent_lock_box_detail);
        Intent intent = getIntent();
        text_subject = (TextView) findViewById(R.id.text_subject);
        text_content = (TextView) findViewById(R.id.text_content);

        MessageItem item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_GENERAL);
        text_content.setText(item.content);
        text_subject.setText(item.subject);
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
                            setActionBarConfig(resJson.isNull("name") ? "" :"To "+ resJson.getString("name"), 8);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
