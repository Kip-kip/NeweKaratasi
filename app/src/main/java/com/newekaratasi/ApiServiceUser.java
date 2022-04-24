package com.newekaratasi;

/**
 * Created by Cyrus on 8/20/2017.
 */


        import com.newekaratasi.POJO.UserInfo;

        import retrofit2.Call;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.POST;


/**
 * Created by csa on 05-May-17.
 */

public interface ApiServiceUser {

    @FormUrlEncoded
    @POST("eKaratasi/Refubished/BackendAffairs/save_user.php")
    Call<UserInfo> insertUserInfo(@Field("name") String name, @Field("email") String email, @Field("phone") String phone, @Field("password") String password, @Field("cpassword") String cpassword);
}