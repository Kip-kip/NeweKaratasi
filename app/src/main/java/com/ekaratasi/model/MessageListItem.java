package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class MessageListItem implements Serializable {
    private String agent_name;
    private String agentt;
    private String customer;
    private String sender;
    private String receiver;
    private String text;
    private String status;
    private String time;






    public MessageListItem(String agent_name, String agentt, String customer, String sender, String receiver ,
                    String text ,String status, String time ) {
        this.agent_name = agent_name;
        this.agentt = agentt;
        this.customer = customer;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public String getAgentt() {
        return agentt;
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

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }
}

