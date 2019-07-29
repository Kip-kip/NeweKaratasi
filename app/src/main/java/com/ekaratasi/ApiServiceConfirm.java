package com.ekaratasi;


import com.ekaratasi.POJO.ConfirmAgent;
import com.ekaratasi.POJO.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceConfirm {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/confirm_agent.php")
    Call<ConfirmAgent> insertConfirmData(@Field("agent") String agent);

}
