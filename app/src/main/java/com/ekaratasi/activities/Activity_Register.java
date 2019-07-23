package com.ekaratasi.activities;

import android.content.Intent;
import android.service.autofill.UserData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ekaratasi.ApiServicePay;
import com.ekaratasi.ApiServiceUser;
import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.MainData;
import com.ekaratasi.POJO.UserInfo;
import com.ekaratasi.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ViewFlipper myflipper;
        Button btnNameNext,btnPhoneNext,btnEmailNext,btnRegister;
        ImageView back1,back2,back3,back4;
        final EditText name,email,phone,password;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);


        myflipper =  findViewById(R.id.flipper);
        btnNameNext=findViewById(R.id.btnNameNext);
        btnPhoneNext=findViewById(R.id.btnPhoneNext);
        btnEmailNext=findViewById(R.id.btnEmailNext);
        btnRegister=findViewById(R.id.btnRegister);
        back1=findViewById(R.id.back1);

        back3=findViewById(R.id.back3);
        back4=findViewById(R.id.back4);

        //User information
        name=findViewById(R.id.edtname);
        email=findViewById(R.id.edtemail);
        phone=findViewById(R.id.edtphone);
        password=findViewById(R.id.edtpass);



        //Btn from Name to Email
        btnNameNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               myflipper.showNext();
            }
        });
        //Btn from Email to phone
        btnEmailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showNext();
            }
        });
        //Btn from Phone to Password
        btnPhoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showNext();
            }
        });
        //Btn Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ekaratasikenya.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ApiServiceUser service = retrofit.create(ApiServiceUser.class);
                UserInfo userinfo = new UserInfo();
                userinfo.setName(name.getText().toString());
                userinfo.setEmail(email.getText().toString());
                userinfo.setPhone(phone.getText().toString());
                userinfo.setPassword(password.getText().toString());
                Call<UserInfo> call = service.insertUserInfo(userinfo.getName(), userinfo.getEmail(), userinfo.getPhone(), userinfo.getPassword());

                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        UserInfo tuongee=response.body();
                        String ongeleshwa=tuongee.getError_msg();

                        Integer num =Integer.parseInt(tuongee.getError());


                        if(num==1){
                            Intent it = new Intent(Activity_Register.this, Activity_Login.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                            finish();
                        }
                        else{
//                            Intent it = new Intent(TransactionDetails_Activity.this, Activity_Transaction_Success.class);
//                            startActivity(it);
//                            overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
//                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }


                });
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showPrevious();
            }
        });

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showPrevious();
            }
        });
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showPrevious();
            }
        });

    }


}
