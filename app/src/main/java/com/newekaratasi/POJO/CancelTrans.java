package com.newekaratasi.POJO;

/**
 * Created by Cyrus on 8/29/2017.
 */

public class CancelTrans {
    String trans_refno,error_msg,error;


    public String getTrans_refno() {
        return trans_refno;
    }

    public void setTrans_refno(String trans_refno) {
        this.trans_refno = trans_refno;
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
