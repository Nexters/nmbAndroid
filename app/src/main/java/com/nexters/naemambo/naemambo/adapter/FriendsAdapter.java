package com.nexters.naemambo.naemambo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.listItem.FriendListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by jjgod on 2016-08-02.
 */
public class FriendsAdapter extends ArrayAdapter<FriendListItem> {
    private Context mContext = null;
    private LayoutInflater inflater = null;
    private ArrayList<Integer> checkedList;
    private boolean chk_friends_visible;

    public FriendsAdapter(Context context, int resource, boolean chk_friends_visible) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.chk_friends_visible = chk_friends_visible;
    }

    public FriendsAdapter(Context context, int resource, boolean chk_friends_visible, int list) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.chk_friends_visible = chk_friends_visible;
        this.checkedList = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
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

        //리스트뷰에 있는 아이템클릭리스너 쓰려면 해줘야함
        holder.chk_friends.setClickable(false);
        holder.chk_friends.setFocusable(false);

        holder.chk_friends.setChecked(item.isChecked());

        holder.chk_friends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedList.add(position);
                } else {
                    checkedList.clear();
                }

            }
        });
        if (chk_friends_visible) {
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


}
