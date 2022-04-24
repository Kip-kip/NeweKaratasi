package com.newekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */

import com.newekaratasi.POJO.EditProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiServiceEditProfile {

    @FormUrlEncoded

    @POST("eKaratasi/Refubished/BackendAffairs/edit_profile.php")
    Call<EditProfile> insertProfileData(@Field("id") String id,@Field("name") String name, @Field("phone") String phone, @Field("email") String email);
        }