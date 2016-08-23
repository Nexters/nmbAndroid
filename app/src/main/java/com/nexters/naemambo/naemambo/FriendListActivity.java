package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nexters.naemambo.naemambo.listItem.FriendListItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendListActivity extends BaseActivity {
    public static final String TAG = FriendListActivity.class.getSimpleName();
    private ActionBar actionBar;
    private TextView action_bar_write_title;
    private ImageView btn_actionbar_back, btn_actionbar_select;
    JSONArray friendslist;
    ArrayList<String> friends = new ArrayList<>();
    ArrayList<String> friendsid = new ArrayList<>();
    ArrayList<String> profile_url = new ArrayList<>();
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

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
        adapter = new CustomAdapter(this, R.layout.message_list_item);
        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

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
                            for (int i = 0; i < friendslist.length(); i++) {
                                JSONObject resJson = friendslist.getJSONObject(i);
                                Log.e(TAG, "onCompleted: resJson  : " + resJson.toString());
                                friends.add(resJson.getString("name"));
                                friendsid.add(resJson.getString("id"));
                                profile_url.add("https://graph.facebook.com/" + resJson.getString("id") + "/picture?type=large");
                            }
                            addItem(friendsid, friends, profile_url);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    private void addItem(ArrayList<String> friendsid, ArrayList<String> friends, ArrayList<String> profile_url) {
        FriendListItem item;
        for (int i = 0; i < friends.size(); i++) {
            item = new FriendListItem();
            item.setId(friendsid.get(i));
            item.setName(friends.get(i));
            item.setProfile_url(profile_url.get(i));
            adapter.add(item);
        }
    }


}
