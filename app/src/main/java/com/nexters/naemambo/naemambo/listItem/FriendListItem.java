package com.nexters.naemambo.naemambo.listItem;

import java.io.Serializable;

/**
 * Created by Jeon on 2016-08-17.
 */
public class FriendListItem implements Serializable {
    public String getImg_profile_img() {
        return img_profile_img;
    }

    public void setImg_profile_img(String img_profile_img) {
        this.img_profile_img = img_profile_img;
    }

    public String getTxt_friends_name() {
        return txt_friends_name;
    }

    public void setTxt_friends_name(String txt_friends_name) {
        this.txt_friends_name = txt_friends_name;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }


    private String img_profile_img;
    private String txt_friends_name;
    private String friend_id;
}
