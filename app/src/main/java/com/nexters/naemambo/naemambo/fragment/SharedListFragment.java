package com.nexters.naemambo.naemambo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.nexters.naemambo.naemambo.MyboxDetailDoneActivity;
import com.nexters.naemambo.naemambo.MyboxDetailGeneralActivity;
import com.nexters.naemambo.naemambo.MyboxDetailShareActivity;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.adapter.MessageAdapter;
import com.nexters.naemambo.naemambo.util.BaseFragment;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.URL_Define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SharedListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private MessageAdapter adapter;
    private ListView receiveList;
    private boolean hasNextItem = true;
    private boolean lastItemVisibleFlag = false;
    int pageIndex = 1;
    private static final String TAG = SharedListFragment.class.getSimpleName();
    private SimpleDateFormat sdfCurrent;
    private TextView txt_empty_box, btn_hope_msg;
    private SwipeRefreshLayout swiperefresh;
    private Dialog dialog_hope_msg;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        adapter = new MessageAdapter(getContext(), R.layout.message_list_item);
        receiveList = (ListView) view.findViewById(R.id.receive_listview);
        receiveList.setAdapter(adapter);

        swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(this);

        txt_empty_box = (TextView) view.findViewById(R.id.txt_empty_box);
        sdfCurrent = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        dialog_hope_msg = new Dialog(getContext(), R.style.dialogStyle);
        dialog_hope_msg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_hope_msg.setContentView(R.layout.dialog_hope_msg);
        btn_hope_msg = (TextView) dialog_hope_msg.findViewById(R.id.btn_hope_msg);

        LoadFromServer(pageIndex);
        receiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int boxType = adapter.getItem(position).boxType;
                if (boxType == Const.GENERAL_BOX) {

                    startActivity(new Intent(getContext(), MyboxDetailGeneralActivity.class)
                            .putExtra(Const.BOX_DETAIL_GENERAL, adapter.getItem(position)));

                } else if (boxType == Const.LOCK_BOX) {
                    //잠금박스 같이보기 요청했을때 서버에 status(2) 변경 요청
                    dialog_hope_msg.show();
                    btn_hope_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e(TAG, "onClick:1111111111111 : " + adapter.getItem(position).boxNo);
                            updateBoxStatus(2, adapter.getItem(position).boxNo);
                            dialog_hope_msg.dismiss();
                        }
                    });
                } else if (boxType == Const.DONE_BOX) {

                    startActivity(new Intent(getContext(), MyboxDetailDoneActivity.class)
                            .putExtra(Const.BOX_DETAIL_DONE, adapter.getItem(position)));
                } else if (boxType == Const.SHARE_BOX) {

                    startActivity(new Intent(getContext(), MyboxDetailShareActivity.class)
                            .putExtra(Const.BOX_DETAIL_SHARE, adapter.getItem(position)));
                }
            }
        });
        //스크롤 맨밑으로 내리면 서버에 pageIndex 증가시켜서 다시요청
        receiveList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    if (hasNextItem) {
                        pageIndex++;
                        LoadFromServer(pageIndex);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount)) {
                    lastItemVisibleFlag = true;
                } else {
                    lastItemVisibleFlag = false;
                }
                boolean enable = false;
                if(receiveList != null && receiveList.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = receiveList.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = receiveList.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swiperefresh.setEnabled(enable);
            }
        });
        return view;
    }

    private void LoadFromServer(int pageIndex) {
        getReq(URL_Define.MY_SHARED_LIST + pageIndex, new RequestParams(), new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.d(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], res = [" + res + "]");
                jsonObject = res;
                setListView(res);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
                Log.e(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], t = [" + t + "], res = [" + res + "]");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);
                Log.e(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], t = [" + t + "]");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume:111111111111111111111111111111111111111111111111111111111111111111");
        swiperefresh.setRefreshing(true);
        adapter.clear();
        pageIndex = 1;
        LoadFromServer(pageIndex);
        swiperefresh.setRefreshing(false);
    }

    private void setListView(JSONObject res) {
        Log.e(TAG, "setListView()");

        try {
            JSONArray array = res.getJSONArray("resData").getJSONObject(0).getJSONObject("list").getJSONArray("content");
            if (array.length() < 10) {
                hasNextItem = false;
            }
            if (array.length() > 0) {
                txt_empty_box.setVisibility(View.GONE);
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject resJson = (JSONObject) array.get(i);
                addItem(adapter
                        , resJson.getInt("status")
                        , resJson.getInt("boxno")
                        , resJson.getString("title")
                        , resJson.getString("content")
                        , sdfCurrent.format(new Timestamp(Long.parseLong(resJson.getString("date"))))
                        , resJson.getString("shuserid")
                        , resJson.isNull("sendname") ? "" : resJson.getString("sendname")
                        , resJson.getString("target"));

            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: onRefreshonRefreshonRefresh onRefresh" );
        adapter.clear();
        pageIndex = 1;
        LoadFromServer(pageIndex);
        swiperefresh.setRefreshing(false);

    }

}
