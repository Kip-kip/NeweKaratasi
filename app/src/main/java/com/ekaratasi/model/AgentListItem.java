package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class AgentListItem implements Serializable {
    private String agent_name;
    private String agent_refno;
    private String phone;
    private String location;
    private String image;







    public AgentListItem(String agent_name, String agent_refno, String phone, String location, String image) {
        this.agent_name = agent_name;
        this.agent_refno=agent_refno;
        this.phone = phone;
        this.location = location;
        this.image = image;

}

    public String getAgent_refno() {
        return agent_refno;
    }

    public void setAgent_refno(String agent_refno) {
        this.agent_refno = agent_refno;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }
}

