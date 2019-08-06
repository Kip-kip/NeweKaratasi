package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class ChatListItem implements Serializable {

    private String agentt;
    private String customer;
    private String sender;
    private String receiver;
    private String text;
    private String time;






    public ChatListItem(String agentt, String customer,String sender, String receiver ,
                           String text ,String time ) {
        this.agentt = agentt;
        this.customer = customer;
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

    public void setAgentt(String agentt) {
        this.agentt = agentt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

}

