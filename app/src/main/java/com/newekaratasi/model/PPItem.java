package com.newekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class PPItem implements Serializable {
    private String profilephoto;







    public PPItem(String profilephoto) {
        this.profilephoto = profilephoto;

    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }
}

