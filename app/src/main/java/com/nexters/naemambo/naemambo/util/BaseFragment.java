package com.nexters.naemambo.naemambo.util;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.listItem.MessageItem;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jjgod on 2016-08-02.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();

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
    public class ConnHttpResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
            if(res!=null) {
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
