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
import com.nexters.naemambo.naemambo.util.Const;

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
        switch (item.boxType) {
            case Const.GENERAL_BOX:
                Glide.with(getContext()).load(R.drawable.list_g_box).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect);
                break;
            case Const.LOCK_BOX:
                Glide.with(getContext()).load(R.drawable.list_lock).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect_lock);
                break;
            case Const.DONE_BOX:
                Glide.with(getContext()).load(R.drawable.list_done).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect);
                break;
            case Const.SHARE_BOX:
                Glide.with(getContext()).load(R.drawable.list_share).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect);
                break;
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
