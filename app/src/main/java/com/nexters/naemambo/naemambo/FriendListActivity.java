package com.nexters.naemambo.naemambo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nexters.naemambo.naemambo.listItem.FriendListItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {
    public static final String TAG = FriendListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist;
        ArrayList<String> friends = new ArrayList<String>();
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
        listView.setAdapter(adapter);


        addItem(adapter, friendsid, friends, profile_url);


    }

    public void addItem(ArrayAdapter<FriendListItem> adapter, ArrayList<String> friendsid, ArrayList<String> friends, ArrayList<String> profile_url) {

        FriendListItem item = null;
        for (int i = 0; i < friends.size(); i++) {
            item = new FriendListItem();
            item.setId(friendsid.get(i));
            item.setName(friends.get(i));
            item.setProfile_url(profile_url.get(i));
            adapter.add(item);
        }


    }


}
