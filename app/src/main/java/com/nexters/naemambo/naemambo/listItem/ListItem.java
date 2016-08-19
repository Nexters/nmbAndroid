package com.nexters.naemambo.naemambo.listItem;

import java.io.Serializable;

/**
 * Created by Jeon on 2016-08-17.
 */
public class ListItem implements Serializable {

    private String id;
    private String name;
    private String profile_url;
    public ListItem(){}

    public ListItem(String id, String name, String profile_url) {
        this.id = id;
        this.name = name;
        this.profile_url=profile_url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", profile_url='" + profile_url + '\'' +
                '}';
    }
}
