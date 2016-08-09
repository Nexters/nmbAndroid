package com.nexters.naemambo.naemambo.listItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jjgod on 2016-08-02.
 */
@ToString
@Getter
@Setter
public class MessageItem {
    public int boxType;
    public String subject;
    public String content;
    public String date;
}
