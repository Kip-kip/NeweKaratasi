package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class TotalTransactionsItem implements Serializable {
    private String transactions;







    public TotalTransactionsItem(String transactions) {
        this.transactions = transactions;

    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }
}

