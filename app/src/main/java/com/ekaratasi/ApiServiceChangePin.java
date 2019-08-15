package com.ekaratasi;


import com.ekaratasi.POJO.ChangePin;
import com.ekaratasi.POJO.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceChangePin {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/change_pin.php")
    Call<ChangePin> insertPinData(@Field("id") String id, @Field("oldpass") String oldpass, @Field("newpass") String newpass, @Field("cpass") String cpass);

}
