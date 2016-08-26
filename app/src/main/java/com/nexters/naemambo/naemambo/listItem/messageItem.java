package com.nexters.naemambo.naemambo.listItem;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jjgod on 2016-08-02.
 */
@ToString
@Getter
@Setter
public class MessageItem implements Serializable {
    public int boxType;
    public String subject;
    public String content;
    public String date;
    public String shuserid;
    public String name;
}
