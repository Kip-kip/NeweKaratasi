package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.ChangePin;
import com.ekaratasi.POJO.EditProfile;
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

public class ChangePin_Activity extends AppCompatActivity {

ImageView back;
TextView txtold,txtnew,txtc,savepin;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpin);

        back=findViewById(R.id.back);

        txtold=findViewById(R.id.txtOldPin);
        txtnew=findViewById(R.id.txtNewPin);
        txtc=findViewById(R.id.txtCPin);

        savepin=findViewById(R.id.savepin);
        // session manager
        session = new SessionManager(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ChangePin_Activity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
                finish();

            }
        });

        savepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangePin();

            }
        });

    }

    public void onBackPressed(){
        Intent it = new Intent(ChangePin_Activity.this, Settings_Activity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

    public void ChangePin(){

        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiServiceChangePin service = retrofit.create(com.ekaratasi.ApiServiceChangePin.class);
        ChangePin cp = new ChangePin();
        cp.setId(user_id);
        cp.setOldpass(txtold.getText().toString());
        cp.setNewpass(txtnew.getText().toString());
        cp.setCpass(txtc.getText().toString());

        Call<ChangePin> call = service.insertPinData(cp.getId(),cp.getOldpass(), cp.getNewpass(),cp.getCpass());

        call.enqueue(new Callback<ChangePin>() {
            @Override
            public void onResponse(Call<ChangePin> call, Response<ChangePin> response) {
                ChangePin tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    Toast.makeText(ChangePin_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();

                    //end session
                session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(ChangePin_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

                }
                else{

                    Toast.makeText(ChangePin_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<ChangePin> call, Throwable t) {

            }


        });

    }


}
