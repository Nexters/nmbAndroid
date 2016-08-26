package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nexters.naemambo.naemambo.adapter.FriendsAdapter;
import com.nexters.naemambo.naemambo.listItem.FriendListItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

import org.json.JSONArray;
import org.json.JSONObject;

public class FriendListActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = FriendListActivity.class.getSimpleName();
    private ActionBar actionBar;
    private TextView action_bar_write_title, txt_count_friends;
    private ImageView btn_actionbar_back, btn_actionbar_select;
    JSONArray friendslist;
    private FriendsAdapter adapter;
    private ListView listView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        intent = new Intent();

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(8);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_frends_layout);
            action_bar_write_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_write_title);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            btn_actionbar_select = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_select);
        }
        listView = (ListView) findViewById(R.id.friends_listview);
        txt_count_friends = (TextView) findViewById(R.id.txt_count_friends);
        adapter = new FriendsAdapter(FriendListActivity.this, R.layout.friend_list_item,true);
        listView.setAdapter(adapter);

        action_bar_write_title.setText("친구 목록");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Log.e(TAG, "facebook res tostring" + response.toString());
                            friendslist = response.getJSONObject().getJSONArray("data");
                            FriendListItem item;
                            for (int i = 0; i < friendslist.length(); i++) {
                                JSONObject resJson = friendslist.getJSONObject(i);
                                Log.e(TAG, "onCompleted: resJson  : " + resJson.toString());
                                item = new FriendListItem();
                                item.setFriend_id(resJson.getString("id"));
                                item.setTxt_friends_name(resJson.getString("name"));
                                item.setImg_profile_img("https://graph.facebook.com/" + resJson.getString("id") + "/picture?type=large");
                                adapter.add(item);
                            }
                            adapter.notifyDataSetChanged();

                            Log.e(TAG, "onCompleted: count : " + adapter.getCount());
                            txt_count_friends.setText(adapter.getCount() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        btn_actionbar_back.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendListItem item = adapter.getItem(position);
                Log.e(TAG, "onItemClick: 1111111111111111111111111111111111111");
                item.setChecked(true);
                Log.e(TAG, "onItemClick: item : " + item.isChecked());
                adapter.notifyDataSetChanged();
                intent.putExtra(Const.FRIENDS_DATA, item);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_actionbar_back:
                finish();
                break;
            case R.id.btn_actionbar_select:
                //seelcel
                selectFriends();
                break;
        }
    }

    private void selectFriends() {
        Log.e(TAG, "selectFriends: 111111111111111" );
        if (adapter.getCheckCount() == 0) {
            Toast.makeText(FriendListActivity.this, "친구를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        } else if (adapter.getCheckCount() > 1) {
            Toast.makeText(FriendListActivity.this, "친구를 한명만 선택해주세요.", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < adapter.getCount(); i++) {
                adapter.getItem(i).setChecked(false);
            }
            adapter.notifyDataSetChanged();
            return;
        } else {
            setResult(RESULT_OK,intent);
        }

    }

}
