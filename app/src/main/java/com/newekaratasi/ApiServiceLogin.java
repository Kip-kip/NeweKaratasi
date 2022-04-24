package com.newekaratasi;


import com.newekaratasi.POJO.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceLogin {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/login.php")
    Call<UserLogin> insertLoginData(@Field("emaili") String emaili, @Field("passi") String passi);

}
