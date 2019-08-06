package com.ekaratasi.POJO;

/**
 * Created by Cyrus on 8/29/2017.
 */

public class SendMessage {
    String agentt,customer,sender,receiver,text,error_msg,error;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
