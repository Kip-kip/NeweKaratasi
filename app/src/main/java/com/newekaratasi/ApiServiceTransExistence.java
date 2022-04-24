package com.newekaratasi;


import com.newekaratasi.POJO.CheckTransExistence;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceTransExistence {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/check_trans_existence.php")
    Call<CheckTransExistence> insertTransExistence(@Field("user_id") String user_id);

}
