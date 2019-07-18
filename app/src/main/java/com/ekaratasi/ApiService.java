package com.ekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */

import com.ekaratasi.POJO.MainData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiService {

    @FormUrlEncoded

    @POST("eKaratasi/Refubished/BackendAffairs/save_transactions.php")
    Call<MainData> insertMainData(@Field("material") String material,@Field("copies") String copies, @Field("bindoption") String bindoption, @Field("bindcolor") String bindcolor, @Field("agent") String agent, @Field("instructions") String instructions);
}