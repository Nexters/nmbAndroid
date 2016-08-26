package com.nexters.naemambo.naemambo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.listItem.FriendListItem;
import com.nexters.naemambo.naemambo.util.SPreference;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by jjgod on 2016-08-02.
 */
public class FriendsAdapter extends ArrayAdapter<FriendListItem> {
    private Context mContext = null;
    private LayoutInflater inflater = null;
    private ArrayList<Integer> checkedList = new ArrayList<>();
    private boolean chkVisibility;

    public FriendsAdapter(Context context, int resource, boolean chkVisibility) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.chkVisibility = chkVisibility;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = null;
        if (convertView == null) {
            holder = new CustomViewHolder();
            convertView = inflater.inflate(R.layout.friend_list_item, parent, false);
            holder.img_profile_img = (ImageView) convertView.findViewById(R.id.img_profile_img);
            holder.txt_friends_name = (TextView) convertView.findViewById(R.id.txt_friends_name);
            holder.chk_friends = (CheckBox) convertView.findViewById(R.id.chk_friends);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        final FriendListItem item = getItem(position);

        Glide.with(getContext()).load(item.getImg_profile_img()).bitmapTransform(new CropCircleTransformation(mContext)).into(holder.img_profile_img);
        holder.txt_friends_name.setText(item.getTxt_friends_name());
//        holder.chk_friends.setChecked(((ListView) parent).isItemChecked(position));
        holder.chk_friends.setClickable(false);
        holder.chk_friends.setFocusable(false);
        holder.chk_friends.setChecked(item.isChecked());


        holder.chk_friends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedList.add(position, 1);
                    item.setChecked(true);
                } else {
                    checkedList.remove(position);
                    item.setChecked(false);
                }
            }
        });
        if (chkVisibility) {
            holder.chk_friends.setVisibility(View.VISIBLE);
        } else {
            holder.chk_friends.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public int getPosition(FriendListItem item) {
        return super.getPosition(item);
    }

    public class CustomViewHolder {
        public ImageView img_profile_img;
        public TextView txt_friends_name;
        public CheckBox chk_friends;
    }

    public int getCheckCount() {
        return checkedList.size();
    }

    public ArrayList<Integer> getCheckedList() {
        return checkedList;
    }
}
