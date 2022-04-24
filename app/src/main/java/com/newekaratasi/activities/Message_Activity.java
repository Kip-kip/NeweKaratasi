package com.newekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newekaratasi.MainActivity;
import com.newekaratasi.R;
import com.newekaratasi.adapter.MessageAdapter;
import com.newekaratasi.helper.SQLiteHandler;
import com.newekaratasi.model.MessageListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Message_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MessageListItem> listItems;

    View loading;
    ImageView noresultimage,nointernet,back;
    TextView noresulttext,nointernettext;
    FloatingActionButton fab;
    private SQLiteHandler db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(Message_Activity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_transactions:
                    Intent itt = new Intent(Message_Activity.this, Transactions_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();


                    return true;

                case R.id.navigation_notifications:
                    Intent ittt= new Intent(Message_Activity.this, Notification_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:
                    Intent itttt = new Intent(Message_Activity.this, Message_Activity.class);
                    startActivity(itttt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_agents:
                    Intent ittttt = new Intent(Message_Activity.this, Agents_Activity.class);
                    startActivity(ittttt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                navigation.setVisibility(View.VISIBLE);

            }
        });

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);


        loading=findViewById(R.id.loadingdots);
        noresultimage=findViewById(R.id.noresultimage);
        noresulttext=findViewById(R.id.noresulttext);
        nointernet=findViewById(R.id.nointernet);
        nointernettext=findViewById(R.id.nointernettext);

        fab=findViewById(R.id.fab);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


//        loadRecyclerViewData();
        loadDummy();

        // Adding click listener to FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Message_Activity.this, MessageItem_Activity.class);

                intent.putExtra("INDIC","CREATE");

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Message_Activity.this.startActivity(intent);


            }
        });



    }




    private void loadDummy(){

        MessageListItem item = new MessageListItem(
                "AGEPRNT",
                "Mwaks Printing Centre",
                "CUST102",
                "Mwaks Printing Centre",
                "USER12",
                "There is a problem with the payment",
                "Read",
                "2021-02-04"

        );

        for(int x=0;x<=2;x++){
            listItems.add(item);
        }

        adapter=new MessageAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
        loading.setVisibility(View.INVISIBLE);
    }
    private void loadRecyclerViewData(){
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");


        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_messages.php?user_id="+user_id+"";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // dismiss loadin
                        loading.setVisibility(View.INVISIBLE);

                        //show no result
                        noresulttext.setVisibility(View.VISIBLE);
                        noresultimage.setVisibility(View.VISIBLE);

                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                MessageListItem item=new MessageListItem(
                                        o.getString("agent_name"),
                                        o.getString("agent"),
                                        o.getString("customer"),
                                        o.getString("sender"),
                                        o.getString("receiver"),
                                        o.getString("text"),
                                        o.getString("status"),
                                        o.getString("time")

                                );

                                //show recycler view and hhide no rsult
                                recyclerView.setVisibility(View.VISIBLE);
                                noresultimage.setVisibility(View.GONE);
                                noresulttext.setVisibility(View.GONE);


                                listItems.add(item);



                            }

                            adapter=new MessageAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyerror) {


                        loading.setVisibility(View.INVISIBLE);

                        nointernet.setVisibility(View.VISIBLE);
                        nointernettext.setVisibility(View.VISIBLE);
                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {




    }

}
