package com.nexters.naemambo.naemambo.util;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.MyboxDetailDoneActivity;
import com.nexters.naemambo.naemambo.MyboxDetailGeneralActivity;
import com.nexters.naemambo.naemambo.MyboxDetailShareActivity;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.listItem.MessageItem;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by jjgod on 2016-08-02.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();
    private Dialog dialog_hope_msg;
    private TextView btn_hope_msg;

    /**
     * 메세지 리스트 아이템
     * @param adapter
     * @param boxType
     * @param subject
     * @param content
     * @param date
     */
    public void addItem(ArrayAdapter<MessageItem> adapter, int boxType, String subject, String content, String date) {
        MessageItem item = new MessageItem();
        item.boxType = boxType;
        item.content = content;
        item.date = date;
        item.subject = subject;
        adapter.add(item);
    }

    public void getReq(String url, RequestParams params, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.setURLEncodingEnabled(true);
        client.get(url, params, responseHandler);
    }

    public void postReq(String url, RequestParams params, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.setURLEncodingEnabled(true);
        client.post(url, params, responseHandler);
    }

    public void postReq(String url, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        client.post(getContext(), url, entity, "application/json", responseHandler);
    }

    public void deleteJson(String url, String token, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.addHeader("Authorization", token);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        client.delete(getContext(), url, entity, "application/json", responseHandler);
    }

    public void putRequest(String url, String token, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.addHeader("Authorization", token);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        client.put(getContext(), url, entity, "application/json", responseHandler);
    }

    public void dialogInit() {
        dialog_hope_msg = new Dialog(getContext(), R.style.dialogStyle);
        dialog_hope_msg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_hope_msg.setContentView(R.layout.dialog_hope_msg);
        btn_hope_msg = (TextView) dialog_hope_msg.findViewById(R.id.btn_hope_msg);

        btn_hope_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBoxStatusShare();
            }
        });
    }

    private void updateBoxStatusShare() {
//        putRequest();
    }

    public void dialog_hope_msg_show() {
        if (dialog_hope_msg != null) {
            dialog_hope_msg.show();
        } else {
            Log.e(TAG, "dialog_hope_msg_show: nullnullnullnull");
        }
    }

    public void dialog_hope_msg_dismiss() {
        if (dialog_hope_msg != null) {
            dialog_hope_msg.dismiss();
        } else {
            Log.e(TAG, "dialog_hope_msg_show: nullnullnullnull");
        }
    }

    public class ConnHttpResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
            if (res != null) {
                Log.e(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], res = [" + res + "]");
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
            if (res != null) {
                Log.e(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], t = [" + t + "], res = [" + res + "]");
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
            if (responseString != null) {
                Log.e(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], Throwable = [" + t + "]");
            }
        }
    }
}
