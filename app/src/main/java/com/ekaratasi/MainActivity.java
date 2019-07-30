package com.ekaratasi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekaratasi.activities.Activity_Login;
import com.ekaratasi.activities.InvoiceItem_Activity;
import com.ekaratasi.activities.MessageItem_Activity;
import com.ekaratasi.activities.Message_Activity;
import com.ekaratasi.activities.Notification_Activity;
import com.ekaratasi.activities.PDFUpload_Activity;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.activities.Transactions_Activity;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.ekaratasi.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ImageView gotosettings,noresultimage;

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

     Button newtransaction,viewall;
    TextView txtwelcome,noresulttext;
    View loading;
    LinearLayout alltransactions;
    private static final String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_transactionsformain.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;


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

        noresultimage=findViewById(R.id.noresultimage);
        noresulttext=findViewById(R.id.noresulttext);
        loading=findViewById(R.id.loadingdots);
        viewall=findViewById(R.id.viewall);
        alltransactions=findViewById(R.id.allTransactions);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

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

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Transactions_Activity.class);
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




        loadRecyclerViewData();

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

    private void loadRecyclerViewData(){


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // dismiss loadin
                        loading.setVisibility(View.INVISIBLE);

                            //show no result
                        noresultimage.setVisibility(View.VISIBLE);
                        noresulttext.setVisibility(View.VISIBLE);


                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                ListItem item=new ListItem(
                                        o.getString("agent"),
                                        o.getString("progress_status"),
                                        o.getString("trans_refno"),
                                        o.getString("customer_refno"),
                                        o.getString("phone"),
                                        o.getString("material"),
                                        o.getString("bind_color"),
                                        o.getString("bind_type"),
                                        o.getString("copies"),
                                        o.getString("instructions"),
                                        o.getString("payment_status"),
                                        o.getString("invoice_status"),
                                        o.getString("time_stamp"),
                                        o.getString("bw_pages"),
                                        o.getString("bw_cost"),
                                        o.getString("c_pages"),
                                        o.getString("c_cost"),
                                        o.getString("total_pages"),
                                        o.getString("bind_cost"),
                                        o.getString("total_cost"),
                                        o.getString("ekaratasi_fee")



                                );


                            //show recycler view and hhide no rsult
                                recyclerView.setVisibility(View.VISIBLE);
                                noresultimage.setVisibility(View.GONE);
                                noresulttext.setVisibility(View.GONE);




                                listItems.add(item);



                            }

                            adapter=new TransactionsAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyerror) {






                        // Toast.makeText(getApplicationContext(),volleyerror.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
