package com.ekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */

import com.ekaratasi.POJO.SendMessage;
import com.ekaratasi.POJO.SendTalk;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiServiceSendTalk {

    @FormUrlEncoded

    @POST("eKaratasi/Refubished/BackendAffairs/save_talk.php")
    Call<SendTalk> insertTalkData(@Field("phone") String phone, @Field("text") String text);
        }