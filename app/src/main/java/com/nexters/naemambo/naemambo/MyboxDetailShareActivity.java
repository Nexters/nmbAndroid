package com.nexters.naemambo.naemambo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

    private TextView btn_complete, text_share_subject, text_share_content, btn_really_solved;
    private static final String TAG = MyboxDetailShareActivity.class.getSimpleName();
    private MessageItem item;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybox_detail_share);
        btn_complete = (TextView) findViewById(R.id.btn_complete);

        dialog = new Dialog(MyboxDetailShareActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_really_solved);
        btn_really_solved = (TextView) dialog.findViewById(R.id.btn_really_solved);

        Intent intent = getIntent();
        text_share_subject = (TextView) findViewById(R.id.text_share_subject);
        text_share_content = (TextView) findViewById(R.id.text_share_content);

        item = (MessageItem) intent.getSerializableExtra(Const.BOX_DETAIL_SHARE);
        text_share_content.setText(item.content);
        if (item.content != null) {
            text_share_subject.setText(item.subject);
        } else {
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
                            setActionBarConfig(resJson.isNull("name") ? "누군가로부터.." : "From " + resJson.getString("name"), 8);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        btn_complete.setOnClickListener(this);
        btn_really_solved.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //문제 해결했을때 서버에 박스 status(3) 변경요청
            case R.id.btn_really_solved:
                Log.e(TAG, "onClick: item boxNo : " + item.boxNo);
                updateBoxStatus(3, item.boxNo);
                dialog.dismiss();
                break;
            case R.id.btn_complete:
                Log.e(TAG, "onClick: item.ToString " + item.toString());
                dialog.show();
                break;
        }
    }


}
