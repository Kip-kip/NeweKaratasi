package com.ekaratasi.model;


import java.io.Serializable;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class ListItem implements Serializable {

    private String agent;
    private String trans_refno;
    private String customer_refno;
    private String phone;
    private String material;
    private String bind_type;
    private String bind_color;
    private String copies;
    private String instructions;
    private String progress_status;
    private String payment_status;
    private String invoice_status;
    private String time_stamp;






    public ListItem(String agent, String progress_status ,
                    String trans_refno , String customer_refno , String phone,
                    String material, String bind_type, String bind_color,
                    String copies, String instructions , String payment_status, String invoice_status, String time_stamp) {
        this.agent = agent;
        this.progress_status=progress_status;
        this.trans_refno = trans_refno;
        this.customer_refno =customer_refno;
        this.phone = phone;
        this.material = material;
        this.bind_color = bind_color;
        this.bind_type = bind_type;
        this.copies = copies;
        this.instructions= instructions;
        this.payment_status = payment_status;
        this.invoice_status = invoice_status;
        this.time_stamp = time_stamp;
    }


    public String getAgent() {
        return agent;
    }

    public String getProgress() {
        return progress_status;
    }

    public String getTrans_refno() {
        return trans_refno;
    }

    public String getCustomer_refno() {
        return customer_refno;
    }

    public String getPhone() {
        return phone;
    }

    public String getMaterial() {
        return material;
    }

    public String getBind_type() {
        return bind_type;
    }

    public String getBind_color() {
        return bind_color;
    }

    public String getCopies() {
        return copies;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getInvoice_status() {
        return invoice_status;
    }

    public String getTime_stamp() {
        return time_stamp;
    }
}

