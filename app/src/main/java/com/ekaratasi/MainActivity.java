package com.ekaratasi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.activities.Activity_Login;
import com.ekaratasi.activities.InvoiceItem_Activity;
import com.ekaratasi.activities.MessageItem_Activity;
import com.ekaratasi.activities.Message_Activity;
import com.ekaratasi.activities.Notification_Activity;
import com.ekaratasi.activities.PDFUpload_Activity;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.activities.Transactions_Activity;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    ImageView gotosettings;

    private SessionManager session;
    private SQLiteHandler db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_transactions:

                    Intent it = new Intent(MainActivity.this, Transactions_Activity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_notifications:
                    Intent itt= new Intent(MainActivity.this, Notification_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:

                    Intent ittt = new Intent(MainActivity.this, Message_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;
            }
            return false;
        }
    };

     Button newtransaction;
    TextView txtwelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);


        newtransaction=findViewById(R.id.btnNewTrans);
        gotosettings=findViewById(R.id.gotosettings);
        txtwelcome=findViewById(R.id.txtWelcome);


        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String username = user.get("username");

       txtwelcome.setText("Welcome,"+" "+username);




        newtransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PDFUpload_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();
            }
        });


        gotosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //end session
                session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(MainActivity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {


    }


    private void logoutUser() {
        session.setLogin(false);
        Intent it = new Intent(MainActivity.this, Activity_Login.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

}
