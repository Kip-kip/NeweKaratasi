package com.newekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class TotalCostItem implements Serializable {
    private String cost;







    public TotalCostItem(String cost) {
        this.cost = cost;

    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

