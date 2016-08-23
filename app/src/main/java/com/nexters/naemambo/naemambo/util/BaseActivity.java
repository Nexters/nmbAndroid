package com.nexters.naemambo.naemambo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.util.TextUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();
    private ActionBar actionBar;
    TextView action_bar_back_title;
    private SPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setActionBar();
        pref = new SPreference(BaseActivity.this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setActionBarConfig(String title, int elevation) {
        action_bar_back_title.setText(title);
        actionBar.setElevation(elevation);
    }

    public void setActionBar() {
        ImageView btn_actionbar_back;
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(6);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_back_layout);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            action_bar_back_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_back_title);

            btn_actionbar_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void deleteBox() {
//        deleteJson();
    }
    private void sessionExpire(String sessionId) {
        if(TextUtils.isEmpty(sessionId)){
            new AlertDialog.Builder(BaseActivity.this)
                    .setMessage("세션이 만료 되었습니다. 로그인 화면으로 이동합니다.")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
    }
    public class ConnHttpResponseHandler extends JsonHttpResponseHandler {
        String sessionId = pref.getString(Const.JSESSIONID, "");

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
            Log.e(TAG, "onSuccess: 11111111111111111111111111111111111");
            if (res != null && !TextUtils.isEmpty(res.toString())) {
                Log.d("Handler", "onSuccess /code : " + statusCode + "/headers : " + headers + "//res : " + res);
            }
            sessionExpire(sessionId);
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            super.onSuccess(statusCode, headers, responseString);
            Log.e(TAG, "onSuccess: 22222222222222222222222222222222");
            sessionExpire(sessionId);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
            Log.e(TAG, "onFailure: 3333333333333333333333333");
            if (res != null && !TextUtils.isEmpty(res.toString())) {
                Log.e(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], t = [" + t + "], res = [" + res + "]");
            }
            sessionExpire(sessionId);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
            Log.e(TAG, "onFailure: 44444444444444444444444444");
            super.onFailure(statusCode, headers, responseString, t);
            sessionExpire(sessionId);
        }

    }

    public void getReq(String url, RequestParams params, ConnHttpResponseHandler responseHandler) {
        String sessionId = pref.getString(Const.JSESSIONID, "");
        Log.e(TAG, "getReq: Seesion did  " + sessionId);
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.setURLEncodingEnabled(true);
        if (!TextUtils.isEmpty(sessionId)) {
            client.addHeader("cookie", sessionId);
        } else {
            Log.e(TAG, "JSESSIONID is empty");
        }
        client.get(url, params, responseHandler);
    }

    public void postReq(String url, RequestParams params, ConnHttpResponseHandler responseHandler) {
        String sessionId = pref.getString(Const.JSESSIONID, "");
        Log.e(TAG, "getReq: Seesion did  " + sessionId);
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        client.setURLEncodingEnabled(true);
        if (!TextUtils.isEmpty(sessionId)) {
            client.addHeader("cookie", sessionId);
        } else {
            Log.e(TAG, "JSESSIONID is empty");
        }
        client.post(url, params, responseHandler);
    }

    public void postReq(String url, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        String sessionId = pref.getString(Const.JSESSIONID, "");
        Log.e(TAG, "getReq: Seesion did  " + sessionId);
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        if (!TextUtils.isEmpty(sessionId)) {
            client.addHeader("cookie", sessionId);
        } else {
            Log.e(TAG, "JSESSIONID is empty");
        }
        client.post(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

    public void deleteJson(String url, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        String string = pref.getString(Const.JSESSIONID, "");
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        if (!TextUtils.isEmpty(string)) {
            client.addHeader("cookie", string);
        } else {
            Log.e(TAG, "JSESSIONID is empty");
        }
        client.delete(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

    public void putRequest(String url, JSONObject jsonObject, ConnHttpResponseHandler responseHandler) {
        String sessionId = pref.getString(Const.JSESSIONID, "");
        client.setTimeout(20000);
        client.setResponseTimeout(20000);
        client.setConnectTimeout(20000);
        client.setLoggingEnabled(true);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        if (!TextUtils.isEmpty(sessionId)) {
            client.addHeader("cookie", sessionId);
        } else {
            Log.e(TAG, "JSESSIONID is empty");
        }
        client.put(getApplicationContext(), url, entity, "application/json", responseHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
