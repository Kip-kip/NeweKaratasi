package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.ChangePin;
import com.ekaratasi.POJO.EditProfile;
import com.ekaratasi.POJO.ForgotPin;
import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPin_Activity extends AppCompatActivity {

    ImageView back;
    TextView txtemail;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpin);

        back=findViewById(R.id.back);

        txtemail=findViewById(R.id.email);


        reset=findViewById(R.id.btnReset);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ForgotPin_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
                finish();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPin();

            }
        });

    }

    public void onBackPressed(){
        Intent it = new Intent(ForgotPin_Activity.this, Activity_Login.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

    public void ForgotPin(){

   OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiServiceForgotPin service = retrofit.create(com.ekaratasi.ApiServiceForgotPin.class);
        ForgotPin fp = new ForgotPin();
         fp.setEmail(txtemail.getText().toString());

        Call<ForgotPin> call = service.insertFPinData(fp.getEmail());

        call.enqueue(new Callback<ForgotPin>() {
            @Override
            public void onResponse(Call<ForgotPin> call, Response<ForgotPin> response) {
                ForgotPin tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    Toast.makeText(ForgotPin_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();



                    Intent it = new Intent(ForgotPin_Activity.this, Activity_Login.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                    finish();

                }
                else{

                    Toast.makeText(ForgotPin_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<ForgotPin> call, Throwable t) {

            }


        });

    }


}