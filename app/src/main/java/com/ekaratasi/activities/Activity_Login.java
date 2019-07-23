package com.ekaratasi.activities;

import android.content.Intent;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ekaratasi.ApiServiceLogin;
import com.ekaratasi.ApiServiceUser;
import com.ekaratasi.MainActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.UserInfo;
import com.ekaratasi.POJO.UserLogin;
import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {

Button login;
TextView toRegister;
EditText emaili,passi;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.btnLogin);
        toRegister=findViewById(R.id.toRegister);
        emaili=findViewById(R.id.emaili);
        passi=findViewById(R.id.passi);
// Session manager
        session = new SessionManager(getApplicationContext());
// SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Activity_Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ekaratasikenya.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ApiServiceLogin service = retrofit.create(ApiServiceLogin.class);
                UserLogin userlogin = new UserLogin();
              userlogin.setEmaili(emaili.getText().toString());
               userlogin.setPassi(passi.getText().toString());
                Call<UserLogin> call = service.insertLoginData(userlogin.getEmaili(), userlogin.getPassi());

                call.enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                        UserLogin tuongee=response.body();
                        String ongeleshwa=tuongee.getError_msg();

                        Integer num =Integer.parseInt(tuongee.getError());

                        Toast.makeText(Activity_Login.this, ongeleshwa, Toast.LENGTH_LONG).show();
                        if(num==1){
                            //save user data to database
                           // db.addUser("db", "db","db", "db");
                            session.setLogin(true);
                            Intent it = new Intent(Activity_Login.this, MainActivity.class);
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
                    public void onFailure(Call<UserLogin> call, Throwable t) {

                    }


                });


            }
        });




        //dont ave account
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();
            }
        });


    }
}
