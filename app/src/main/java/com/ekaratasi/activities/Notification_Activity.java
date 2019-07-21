package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.adapter.NotificationAdapter;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.model.NotificationListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification_Activity extends AppCompatActivity {
    //    private static String car="Not_Paid";
//    private static String carr="Sent";  https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_transactions.php?car="+car+"&carr="+carr
    private static final String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_notifications.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NotificationListItem> listItems;

    View loading;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(Notification_Activity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_transactions:
                    Intent itt = new Intent(Notification_Activity.this, Transactions_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();


                    return true;

                case R.id.navigation_notifications:
                    Intent ittt= new Intent(Notification_Activity.this, Notification_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:
                    Intent itttt = new Intent(Notification_Activity.this, Message_Activity.class);
                    startActivity(itttt);
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
        setContentView(R.layout.activity_notification);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);

        loading=findViewById(R.id.loadingdots);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        loadRecyclerViewData();

    }


    private void loadRecyclerViewData(){


//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading data....");
//        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // progressDialog.dismiss();
                        loading.setVisibility(View.INVISIBLE);


                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                NotificationListItem item=new NotificationListItem(
                                        o.getString("trans_id"),
                                        o.getString("agent_refno"),
                                        o.getString("type"),
                                        o.getString("message"),
                                        o.getString("status"),
                                        o.getString("time")

                                );


                                listItems.add(item);



                            }

                            adapter=new NotificationAdapter(listItems,getApplicationContext());
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Notification_Activity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();

    }


}