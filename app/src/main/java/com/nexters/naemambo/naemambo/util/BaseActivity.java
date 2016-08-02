package com.nexters.naemambo.naemambo.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
    }

    public void setActionBar() {
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(8);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_back_layout);
        }
    }

    public class ConnHttpResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
            Log.d("Handler", "onSuccess /code : " + statusCode + "/headers : " + headers + "//res : " + res);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {

            if (res != null) {
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], t = [" + t + "], res = [" + res + "]");
            }
            t.printStackTrace();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {

            if (responseString != null) {
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], t = [" + t + "]");
            }
            t.printStackTrace();
        }

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
        client.post(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

    public void deleteJson(String url, String token, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.addHeader("Authorization", token);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        client.delete(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

    public void putRequest(String url, String token, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.addHeader("Authorization", token);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        client.put(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

}
