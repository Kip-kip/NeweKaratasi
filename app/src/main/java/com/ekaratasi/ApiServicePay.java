package com.ekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */


import com.ekaratasi.POJO.UserPay;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiServicePay {

    @FormUrlEncoded
    @POST("eKaratasi/pay_second/STKprocessor.php")
    Call<UserPay> insertPaymentData(@Field("phone") String phone, @Field("cash") String cash, @Field("trans_refno") String trans_refno);
}