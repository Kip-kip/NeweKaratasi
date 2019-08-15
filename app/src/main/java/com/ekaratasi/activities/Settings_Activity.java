package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;

import java.util.HashMap;

public class Settings_Activity extends AppCompatActivity {

    TextView logout,txtname,txtphone;
    Button editProfile;
    Button changePin,share;
    ImageView back;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logout = findViewById(R.id.txtLogout);
        editProfile=findViewById(R.id.editProfile);
        changePin=findViewById(R.id.changePin);
        back=findViewById(R.id.back);
        share=findViewById(R.id.share);

        txtphone=findViewById(R.id.textMobileNumber);
        txtname=findViewById(R.id.textName);


        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("username");
        String phone = user.get("phone");

        txtphone.setText(phone);
        txtname.setText(name);

        // session manager
        session = new SessionManager(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Settings_Activity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //end session
                session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(Settings_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });



        //TO EDIT PROFILE ACTIVITY
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Settings_Activity.this, EditProfile_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();

            }
        });


        //TO CHANGE PIN
        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Settings_Activity.this, ChangePin_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);

                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="Download the eKaratasi app on google play store: https://play.google.com/store/apps/details?id=com.eKaratasi";
                String shareSub="Download the eKaratasi app on google play store: https://play.google.com/store/apps/details?id=com.eKaratasi";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share via"));

            }
        });

    }

    public void onBackPressed(){
        Intent it = new Intent(Settings_Activity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

}
