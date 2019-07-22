package com.ekaratasi.activities;

import android.content.Intent;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.ekaratasi.MainActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;

import org.json.JSONObject;

import java.util.Locale;

public class Activity_Login extends AppCompatActivity {

Button login;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.btnLogin);
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


        db.addUser("db", "db","db", "db");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                session.setLogin(true);
                Intent it = new Intent(Activity_Login.this,MainActivity.class);
                startActivity(it);
            }
        });



    }
}
