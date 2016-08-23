package com.nexters.naemambo.naemambo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.listItem.MessageItem;
import com.nexters.naemambo.naemambo.util.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jjgod on 2016-08-02.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem> {
    private Context mContext = null;
    private ArrayList<MessageItem> mListData = null;
    private LayoutInflater inflater = null;
    private static final String TAG = MessageAdapter.class.getSimpleName();
    CustomViewHolder holder = null;

    public MessageAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + item.shuserid,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, "onCompleted: " + response.toString());
                        JSONObject resJson = response.getJSONObject();
                        try {
                            holder.msgSubject.setText(resJson.isNull("name") ? "" : "With " + resJson.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        holder.msgContent.setText(item.content);
        holder.msgDate.setText(item.date);


        switch (item.boxType) {
            case Const.GENERAL_BOX:
                Glide.with(getContext()).load(R.drawable.list_g_box).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect);
                break;
            case Const.LOCK_BOX:
                Glide.with(getContext()).load(R.drawable.list_lock).into(holder.msgBox);
                holder.msgBox.setBackgroundResource(R.drawable.msg_box_rect_lock);
                holder.msgContent.setText("메세지가 잠겨있어요.\n상대박에게 메세지 확인 요청을 보내세요!");
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
