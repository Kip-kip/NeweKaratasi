package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transactions_Activity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private SQLiteHandler db;

    View loading;
    ImageView noresultimage,nointernet;
    TextView noresulttext,nointernettext;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_transactions:
                    Intent itt = new Intent(Transactions_Activity.this, Transactions_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();


                    return true;

                case R.id.navigation_home:
                    Intent it = new Intent(Transactions_Activity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_notifications:
                    Intent ittt= new Intent(Transactions_Activity.this, Notification_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:
                    Intent itttt = new Intent(Transactions_Activity.this, Message_Activity.class);
                    startActivity(itttt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_agents:
                    Intent ittttt = new Intent(Transactions_Activity.this, Agents_Activity.class);
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
        setContentView(R.layout.activity_transactions);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);




        loading=findViewById(R.id.loadingdots);
        noresultimage=findViewById(R.id.noresultimage);
        noresulttext=findViewById(R.id.noresulttext);
        nointernet=findViewById(R.id.nointernet);
        nointernettext=findViewById(R.id.nointernettext);



        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();




//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        loadRecyclerViewData();

    }


    private void loadRecyclerViewData(){
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_transactions.php?user_id="+user_id+"";

//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading data....");
//        progressDialog.show();

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
                                ListItem item=new ListItem(
                                        o.getString("agent"),
                                        o.getString("agent_name"),
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
                                        o.getString("ekaratasi_fee"),
                                        o.getString("doc_path")



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
        Intent i = new Intent(Transactions_Activity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();

    }


}
