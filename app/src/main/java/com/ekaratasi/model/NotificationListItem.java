package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class NotificationListItem implements Serializable {
    private String id;
    private String trans_id;
    private String agent;
    private String agent_name;
    private String type;
    private String message;
    private String status;
    private String time;






    public NotificationListItem(String id,String trans_id, String agent , String agent_name, String type,
                           String message , String status,String time ) {
        this.id = id;
        this.trans_id = trans_id;
        this.agent = agent;
        this.agent_name=agent_name;
        this.type = type;
        this.message = message;
        this.status = status;
        this.time = time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void setTime(String time) {
        this.time = time;
    }
}

