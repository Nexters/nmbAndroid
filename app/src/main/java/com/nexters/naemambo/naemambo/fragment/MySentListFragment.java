package com.nexters.naemambo.naemambo.fragment;

import android.app.Dialog;
import android.content.Intent;
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
import com.nexters.naemambo.naemambo.MySentLockBoxDetailActivity;
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


public class MySentListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private MessageAdapter adapter;
    private ListView mySendListView;
    private Dialog dialog_hope_msg;
    private TextView btn_hope_msg;
    private boolean hasNextItem = true;
    private boolean lastItemVisibleFlag = false;
    int pageIndex = 1;
    private static final String TAG = MySentListFragment.class.getSimpleName();
    private SimpleDateFormat sdfCurrent;
    private TextView txt_empty_box;
    private SwipeRefreshLayout my_send_swiperefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_send, container, false);
        adapter = new MessageAdapter(getContext(), R.layout.message_list_item);
        mySendListView = (ListView) view.findViewById(R.id.my_send_listview);
        my_send_swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.my_send_swiperefresh);
        mySendListView.setAdapter(adapter);
        my_send_swiperefresh.setOnRefreshListener(this);
        LoadFromServer(pageIndex);

        txt_empty_box = (TextView) view.findViewById(R.id.txt_empty_box);
        sdfCurrent = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


//        addItem(adapter, 0, "테스트제목1", "날 으아으아 하게 만들어줘", "2016.08.01");
//        addItem(adapter, 1, "테스트제목2", "내 마음이 보이니 테스트 넥스터즈 전한경 최봉재 임주현 ", "2016.08.02");
//        addItem(adapter, 2, "테스트제목3", "너에게 하고싶은 이야기를 여기에 쓴다 내 이야기를 듣고 같이 해결했으면 좋겠어...", "2016.08.01");
//        adapter.notifyDataSetChanged();
        //서버에서 데이터 가져와서 리스트에 넣으세요.
        mySendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int boxType = adapter.getItem(position).boxType;
                if (boxType == Const.GENERAL_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailGeneralActivity.class).putExtra(Const.BOX_DETAIL_GENERAL, adapter.getItem(position)));
                } else if (boxType == Const.LOCK_BOX) {
                    startActivity(new Intent(getContext(), MySentLockBoxDetailActivity.class).putExtra(Const.BOX_DETAIL_GENERAL, adapter.getItem(position)));
                } else if (boxType == Const.DONE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailDoneActivity.class).putExtra(Const.BOX_DETAIL_DONE, adapter.getItem(position)));
                } else if (boxType == Const.SHARE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailShareActivity.class).putExtra(Const.BOX_DETAIL_SHARE, adapter.getItem(position)));
                }
            }
        });
        mySendListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                if(mySendListView != null && mySendListView.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = mySendListView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mySendListView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                my_send_swiperefresh.setEnabled(enable);
            }
        });
        return view;
    }

    private void LoadFromServer(int pageIndex) {
        getReq(URL_Define.MY_SEND_LIST + pageIndex, new RequestParams(), new ConnHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                super.onSuccess(statusCode, headers, res);
                Log.d(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], res = [" + res + "]");
                setListView(res);
                my_send_swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                super.onFailure(statusCode, headers, t, res);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                super.onFailure(statusCode, headers, responseString, t);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        my_send_swiperefresh.setRefreshing(true);
        adapter.clear();
        pageIndex = 1;
        LoadFromServer(pageIndex);
        my_send_swiperefresh.setRefreshing(false);
    }

    private void setListView(JSONObject res) {
        Log.e(TAG, "setListView: ");
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
                        , resJson.isNull("label") ? "" : resJson.getString("label")
                        , resJson.getString("target"));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: onRefreshonRefreshonRefresh onRefresh");
        adapter.clear();
        pageIndex = 1;
        LoadFromServer(pageIndex);
        my_send_swiperefresh.setRefreshing(false);
    }
}
