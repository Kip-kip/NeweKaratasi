package com.newekaratasi;



import com.newekaratasi.POJO.UpdateRegid;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceUpdateRegid {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/update_regid.php")
    Call<UpdateRegid> insertRegidData(@Field("id") String id, @Field("regid") String regid);

}
