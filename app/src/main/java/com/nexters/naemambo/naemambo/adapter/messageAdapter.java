package com.nexters.naemambo.naemambo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.listItem.MessageItem;

import java.util.ArrayList;

/**
 * Created by jjgod on 2016-08-02.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem> {
    private Context mContext = null;
    private ArrayList<MessageItem> mListData = null;
    private LayoutInflater inflater = null;

    public MessageAdapter(Context context, int resource) {
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
            holder.msgBox = (ImageView) convertView.findViewById(R.id.item_msg_box);
            holder.msgSubject = (TextView) convertView.findViewById(R.id.item_msg_subject);
            holder.msgDate = (TextView) convertView.findViewById(R.id.item_msg_date);
            holder.msgContent = (TextView) convertView.findViewById(R.id.item_msg_content);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        MessageItem item = getItem(position);
        if (item.boxType == 0) {
            Glide.with(getContext()).load(R.drawable.lock_box).into(holder.msgBox);
        } else if (item.boxType == 1) {
            Glide.with(getContext()).load(R.drawable.love_box).into(holder.msgBox);
        } else if (item.boxType == 2) {
            Glide.with(getContext()).load(R.drawable.share_box).into(holder.msgBox);
        }
        holder.msgContent.setText(item.content);
        holder.msgSubject.setText(item.subject);
        holder.msgDate.setText(item.date);
        return convertView;
    }

    @Override
    public int getPosition(MessageItem item) {
        return super.getPosition(item);
    }

    public class CustomViewHolder {
        public ImageView msgBox;
        public TextView msgSubject;
        public TextView msgContent;
        public TextView msgDate;
    }
}
