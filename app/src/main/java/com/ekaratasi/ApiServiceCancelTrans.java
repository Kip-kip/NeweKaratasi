package com.ekaratasi;


import com.ekaratasi.POJO.CancelTrans;
import com.ekaratasi.POJO.CheckLeastTrans;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceCancelTrans {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/cancel_transaction.php")
    Call<CancelTrans> insertCancelData(@Field("trans_refno") String trans_refno);

}
