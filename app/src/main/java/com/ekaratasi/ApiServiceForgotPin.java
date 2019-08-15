package com.ekaratasi;


import com.ekaratasi.POJO.ChangePin;
import com.ekaratasi.POJO.ForgotPin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceForgotPin {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/forgot_password.php")
    Call<ForgotPin> insertFPinData(@Field("email") String email);

}
