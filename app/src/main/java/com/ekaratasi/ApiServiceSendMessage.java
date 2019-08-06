package com.ekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */

import com.ekaratasi.POJO.SendMessage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiServiceSendMessage {

    @FormUrlEncoded

    @POST("eKaratasi/Refubished/BackendAffairs/save_message.php")
    Call<SendMessage> insertMessageData(@Field("agentt") String agentt, @Field("customer") String customer, @Field("sender") String sender, @Field("receiver") String receiver, @Field("text") String text);
        }