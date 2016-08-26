package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.nexters.naemambo.naemambo.adapter.FriendsAdapter;
import com.nexters.naemambo.naemambo.listItem.FriendListItem;
import com.nexters.naemambo.naemambo.util.BaseActivity;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.SPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyPageActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = MyPageActivity.class.getSimpleName();
    private ActionBar actionBar;
    private TextView action_bar_write_title, txt_count_friends, txt_myprofile_name;
    private ImageView btn_actionbar_back, btn_actionbar_select, img_myprofile;
    JSONArray friendslist;
    private FriendsAdapter adapter;
    private ListView listView;
    private Profile profile;
    private SPreference sPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(8);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_mypage_layout);
            action_bar_write_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_write_title);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            //btn_actionbar_select = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_select);
        }
        adapter = new FriendsAdapter(this, R.layout.friend_list_item,false);
        listView = (ListView) findViewById(R.id.friends_listview);
        txt_count_friends = (TextView) findViewById(R.id.txt_count_friends);
        txt_myprofile_name = (TextView) findViewById(R.id.txt_myprofile_name);
        img_myprofile = (ImageView) findViewById(R.id.img_myprofile);

        sPreference = new SPreference(MyPageActivity.this);

        txt_myprofile_name.setText(sPreference.getString(Const.USER_NAME, ""));
        Glide.with(MyPageActivity.this).load(sPreference.getString(Const.USER_URL, "")).bitmapTransform(new CropCircleTransformation(MyPageActivity.this)).into(img_myprofile);


        listView.setAdapter(adapter);
        action_bar_write_title.setText("My Page");
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
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendListItem item = adapter.getItem(position);
                boolean isChecked = ((ListView) view).isItemChecked(position);
                Log.e(TAG, "onItemClick: isChecked : " + isChecked);

            }
        });*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_actionbar_back:
                finish();
                break;
        }
    }

}
