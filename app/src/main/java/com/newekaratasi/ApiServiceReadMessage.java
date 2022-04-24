package com.newekaratasi;


import com.newekaratasi.POJO.ReadMessage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceReadMessage {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/read_message.php")
    Call<ReadMessage> insertReadMessageData(@Field("agentt") String agentt, @Field("customer") String customer);

}
