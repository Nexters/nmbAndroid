package com.nexters.naemambo.naemambo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jjgod on 2016-08-02.
 */
public class CustomAdapter extends ArrayAdapter<ListItem> {
    private Context mContext = null;
    private ArrayList<ListItem> mListData = null;
    private LayoutInflater inflater = null;

    public CustomAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = null;
        if (convertView == null) {
            holder = new CustomViewHolder();
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
            holder.profile_img = (ImageView) convertView.findViewById(R.id.profile_img);
            holder.id = (TextView) convertView.findViewById(R.id.id);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        ListItem item = getItem(position);

        Glide.with(getContext()).load(item.getProfile_url()).into(holder.profile_img);
        holder.profile_img.setBackgroundResource(R.drawable.msg_box_rect);


        holder.id.setText(item.getId());
        holder.name.setText(item.getName());

        return convertView;
    }

    @Override
    public int getPosition(ListItem item) {
        return super.getPosition(item);
    }

    public class CustomViewHolder {
        public ImageView profile_img;
        public TextView id;
        public TextView name;

    }
}
