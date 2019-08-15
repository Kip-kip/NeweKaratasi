package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.EditProfile;
import com.ekaratasi.POJO.MainData;
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

public class EditProfile_Activity extends AppCompatActivity {

ImageView back;
EditText txtName,txtPhone,txtEmail;
TextView saveprofile;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        back=findViewById(R.id.back);
        saveprofile=findViewById(R.id.saveprofile);

        txtEmail=findViewById(R.id.txtEmail);
        txtPhone=findViewById(R.id.txtPhone);
        txtName=findViewById(R.id.txtName);

        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("username");
        String phone = user.get("phone");
        String email = user.get("created_at");


        txtPhone.setText(phone);
        txtName.setText(name);
        txtEmail.setText(email);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(EditProfile_Activity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);

                finish();
            }
        });

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             EditProfile();

            //end session
                 session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(EditProfile_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });

    }

    public void onBackPressed(){
        Intent it = new Intent(EditProfile_Activity.this, Settings_Activity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

    public void EditProfile(){

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
        com.ekaratasi.ApiServiceEditProfile service = retrofit.create(com.ekaratasi.ApiServiceEditProfile.class);
        EditProfile ep = new EditProfile();
        ep.setId(user_id);
        ep.setName(txtName.getText().toString());
        ep.setPhone(txtPhone.getText().toString());
        ep.setEmail(txtEmail.getText().toString());

        Call<EditProfile> call = service.insertProfileData(ep.getId(),ep.getName(), ep.getPhone(),ep.getEmail());

        call.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                EditProfile tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    Toast.makeText(EditProfile_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();

                }
                else{



                }
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {

            }


        });

    }

}
