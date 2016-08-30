package com.nexters.naemambo.naemambo.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.LoginActivity;
import com.nexters.naemambo.naemambo.listItem.MessageItem;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.util.TextUtils;

/**
 * Created by jjgod on 2016-08-02.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();
    private Dialog dialog_hope_msg;
    private TextView btn_hope_msg;
    private SPreference pref;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pref = new SPreference(context);
    }

    /**
     * 메세지 리스트 아이템
     *
     * @param adapter
     * @param boxType
     * @param subject
     * @param content
     * @param date
     */
    public void addItem(ArrayAdapter<MessageItem> adapter, int boxType, int boxNo, String subject, String content, String date, String shuserid, String name,String target) {
        MessageItem item = new MessageItem();
        item.boxType = boxType;
        item.boxNo = boxNo;
        item.content = content;
        item.date = date;
        item.subject = subject;
        item.shuserid = shuserid;
        item.name = name;
        item.target = target;
        adapter.add(item);
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
        Log.e(TAG, "postReq: Seesion did  " + sessionId);
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
        client.post(getContext(), url, entity, "application/json", responseHandler);
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
        client.delete(getContext(), url, entity, "application/json", responseHandler);
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
        client.put(getContext(), url, entity, "application/json", responseHandler);
    }


    public void updateBoxStatus(int status, int boxNo) {
        RequestParams params = new RequestParams();
        params.put("status", status);
        postReq(URL_Define.UPDATE_BOX_STATUS + boxNo + "/status", params, new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.e(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], res = [" + res + "]");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
                Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "]");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);
                Log.e(TAG, "onFailure: statusCode : " + statusCode);
            }
        });
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

    private void sessionExpire(String sessionId) {
        if (TextUtils.isEmpty(sessionId)) {
            new AlertDialog.Builder(getContext())
                    .setMessage("세션이 만료 되었습니다. 로그인 화면으로 이동합니다.")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED));
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
}
