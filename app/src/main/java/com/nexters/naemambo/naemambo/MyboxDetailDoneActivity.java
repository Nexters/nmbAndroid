package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class MyboxDetailDoneActivity extends BaseActivity {

    private TextView text_done_subject, text_done_content, text_target_date;
    private static final String TAG = MyboxDetailDoneActivity.class.getSimpleName();
    private String fromTo = "To ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_done);
        Intent intent = getIntent();
        text_done_subject = (TextView) findViewById(R.id.text_done_subject);
        text_done_content = (TextView) findViewById(R.id.text_done_content);
        text_target_date = (TextView) findViewById(R.id.text_target_date);

        MessageItem item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_DONE);
        if (item.content != null) {
            text_done_subject.setText(item.subject);
        } else {
            text_done_subject.setVisibility(View.GONE);
        }

        text_done_content.setText(item.content);
        text_target_date.setText(item.target);
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
                            setActionBarConfig(resJson.isNull("name") ? "누군가로부터.." : fromTo + resJson.getString("name"), 8);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
