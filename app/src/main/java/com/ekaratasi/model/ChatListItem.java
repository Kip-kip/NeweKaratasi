package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class ChatListItem implements Serializable {

    private String sender;
    private String receiver;
    private String text;
    private String time;






    public ChatListItem(String sender, String receiver ,
                           String text ,String time ) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}

