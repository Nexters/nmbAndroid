package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.listItem.FriendListItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendListActivity extends BaseActivity {
    public static final String TAG = FriendListActivity.class.getSimpleName();
    private ActionBar actionBar;
    private TextView action_bar_write_title;
    private ImageView btn_actionbar_back, btn_actionbar_select;

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

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra(Const.FRIENDS_LIST);

        JSONArray friendslist;
        ArrayList<String> friends = new ArrayList<>();
        ArrayList<String> friendsid = new ArrayList<String>();
        ArrayList<String> profile_url = new ArrayList<String>();

        try {

            friendslist = new JSONArray(jsondata);
            for (int l = 0; l < friendslist.length(); l++) {
                friends.add(friendslist.getJSONObject(l).getString("name"));
                friendsid.add(friendslist.getJSONObject(l).getString("id"));
                profile_url.add("https://graph.facebook.com/" + friendslist.getJSONObject(l).getString("id") + "/picture?type=large");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CustomAdapter adapter = new CustomAdapter(this, R.layout.message_list_item);
        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(adapter);
        }


        addItem(adapter, friendsid, friends, profile_url);


    }

    public void addItem(ArrayAdapter<FriendListItem> adapter, ArrayList<String> friendsid, ArrayList<String> friends, ArrayList<String> profile_url) {

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
