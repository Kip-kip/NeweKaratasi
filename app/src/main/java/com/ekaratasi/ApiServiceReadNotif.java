package com.ekaratasi;


import com.ekaratasi.POJO.ConfirmAgent;
import com.ekaratasi.POJO.ReadNotification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Cyrus on 9/26/2017.
 */

public interface ApiServiceReadNotif {
    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/read_notification.php")
    Call<ReadNotification> insertReadNotifData(@Field("id") String id);

}
