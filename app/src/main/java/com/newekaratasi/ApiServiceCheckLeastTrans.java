package com.newekaratasi;


import com.newekaratasi.POJO.CheckLeastTrans;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceCheckLeastTrans {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/check_least_transaction.php")
    Call<CheckLeastTrans> insertLeastTransData(@Field("phone") String phone, @Field("trans_refno") String trans_refno);

}
