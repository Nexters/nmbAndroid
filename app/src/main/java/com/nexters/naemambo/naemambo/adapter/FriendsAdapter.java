package com.nexters.naemambo.naemambo.adapter;

import android.content.Context;
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

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by jjgod on 2016-08-02.
 */
public class FriendsAdapter extends ArrayAdapter<FriendListItem> {
    private Context mContext = null;
    private LayoutInflater inflater = null;
    private boolean[] isCheckedConfrim;

    public FriendsAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        holder.chk_friends.setChecked(((ListView) parent).isItemChecked(position));
        holder.chk_friends.setClickable(false);
        holder.chk_friends.setFocusable(false);
        holder.chk_friends.setChecked(isCheckedConfrim[position]);

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
    // CheckBox를 모두 선택하는 메서드
    public void setAllChecked(boolean isChecked) {
        int tempSize = isCheckedConfrim.length;
        for(int i=0 ; i<tempSize ; i++){
            isCheckedConfrim[i] = isChecked;
        }
    }

    public void setChecked(int position) {
        isCheckedConfrim[position] = !isCheckedConfrim[position];
    }

    public ArrayList<Integer> getChecked(){
        int tempSize = isCheckedConfrim.length;
        ArrayList<Integer> mArrayList = new ArrayList<>();
        for(int b=0 ; b<tempSize ; b++){
            if(isCheckedConfrim[b]){
                mArrayList.add(b);
            }
        }
        return mArrayList;
    }

}
