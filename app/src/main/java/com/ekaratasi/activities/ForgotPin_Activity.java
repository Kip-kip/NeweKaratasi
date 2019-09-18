package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    TextView txtemail,trouble;
    Button reset;
    LinearLayout loadingview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpin);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        back=findViewById(R.id.back);
        trouble=findViewById(R.id.trouble);
        txtemail=findViewById(R.id.email);
        loadingview=findViewById(R.id.loading_view);


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

        //trouble logging in send email
        trouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] strTo = { "info@ekaratasikenya.com" };
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Log in problem(supply email and phone)");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide login button
                reset.setVisibility(View.INVISIBLE);
                //unhide loading view
                loadingview.setVisibility(View.VISIBLE);

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

                    reset.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.INVISIBLE);

                    Intent it = new Intent(ForgotPin_Activity.this, Activity_Login.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                    finish();

                }
                else{

                    Toast.makeText(ForgotPin_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();
reset.setVisibility(View.VISIBLE);
loadingview.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ForgotPin> call, Throwable t) {
                reset.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.INVISIBLE);
            }


        });

    }


}
