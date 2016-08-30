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
    public int boxNo;
    public String subject;
    public String content;
    public String date;
    public String shuserid;

    @Override
    public String toString() {
        return "MessageItem{" +
                "boxType=" + boxType +
                ", boxNo=" + boxNo +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", shuserid='" + shuserid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String name;
}
