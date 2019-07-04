package com.ekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */

import com.ekaratasi.POJO.MainData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("eKaratasi/pages_user.php")
    Call<MainData> insertMainData(@Field("material") String material, @Field("bindoption") String bindoption, @Field("bindcolor") String bindcolor, @Field("instructions") String instructions,@Field("copies") String copies, @Field("agent") String agent);
}