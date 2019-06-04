package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class ListItem implements Serializable {

    private String name;
    private String price;





    public ListItem(String name, String price) {
        this.name = name;
        this.price=price;

    }


    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }



}

