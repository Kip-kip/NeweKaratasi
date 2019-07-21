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

    private String bw_pages;
    private String bw_cost;
    private String c_pages;
    private String c_cost;
    private String total_pages;
    private String bind_cost;
    private String total_cost;
    private String ekaratasi_fee;






    public ListItem(String agent, String progress_status ,
                    String trans_refno , String customer_refno , String phone,
                    String material, String bind_type, String bind_color,
                    String copies, String instructions , String payment_status, String invoice_status, String time_stamp, String bw_pages, String bw_cost,
                    String c_pages, String c_cost, String total_pages, String bind_cost,String total_cost, String ekaratasi_fee) {
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
        this.bw_pages = bw_pages;
        this.bw_cost = bw_cost;
        this.c_pages = c_pages;
        this.c_cost = c_cost;
       this.total_pages = total_pages;
        this.bind_cost = bind_cost;
        this.total_cost = total_cost;
        this.ekaratasi_fee =ekaratasi_fee;
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

    public String getBw_pages() {
        return bw_pages;
    }

    public void setBw_pages(String bw_pages) {
        this.bw_pages = bw_pages;
    }

    public String getBw_cost() {
        return bw_cost;
    }

    public void setBw_cost(String bw_cost) {
        this.bw_cost = bw_cost;
    }

    public String getC_pages() {
        return c_pages;
    }

    public void setC_pages(String c_pages) {
        this.c_pages = c_pages;
    }

    public String getC_cost() {
        return c_cost;
    }

    public void setC_cost(String c_cost) {
        this.c_cost = c_cost;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getBind_cost() {
        return bind_cost;
    }

    public void setBind_cost(String bind_cost) {
        this.bind_cost = bind_cost;
    }

    public String getEkaratasi_fee() {
        return ekaratasi_fee;
    }

    public void setEkaratasi_fee(String ekaratasi_fee) {
        this.ekaratasi_fee = ekaratasi_fee;
    }
}

