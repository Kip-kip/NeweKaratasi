package com.ekaratasi;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.ekaratasi.POJO.EditProfile;
import com.ekaratasi.POJO.UpdateRegid;
import com.ekaratasi.activities.Activity_Login;
import com.ekaratasi.activities.EditProfile_Activity;
import com.ekaratasi.activities.InvoiceItem_Activity;
import com.ekaratasi.activities.MessageItem_Activity;
import com.ekaratasi.activities.Message_Activity;
import com.ekaratasi.activities.Notification_Activity;
import com.ekaratasi.activities.PDFUpload_Activity;
import com.ekaratasi.activities.Settings_Activity;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.activities.Transactions_Activity;
import com.ekaratasi.adapter.MessageAdapter;
import com.ekaratasi.adapter.NotificationAdapter;
import com.ekaratasi.adapter.TotalTransactionsAdapter;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.app.Config;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.ekaratasi.model.ListItem;
import com.ekaratasi.model.MessageListItem;
import com.ekaratasi.model.NotificationListItem;
import com.ekaratasi.model.TotalCostItem;
import com.ekaratasi.model.TotalTransactionsItem;
import com.ekaratasi.service.PersistService;
import com.ekaratasi.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ImageView gotosettings,noresultimage,nointernet,belloff,bellon;

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
    TextView txtwelcome,noresulttext,nointernettext,totalcost,totaltransactions;
    View loading;
    LinearLayout alltransactions;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private List<NotificationListItem> listItemsnotif;
    private List<MessageListItem> listItemsmessage;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView  txtRegId,notifbadge,messagebadge;
    private View notificationBadge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);






        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);

        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);



        BottomNavigationMenuView bottomNavigationMenuView1 = (BottomNavigationMenuView) navigation.getChildAt(0);
        View v1 = bottomNavigationMenuView1.getChildAt(3);

        BottomNavigationItemView itemView1 = (BottomNavigationItemView) v1;

        View badge1 = LayoutInflater.from(this)
                .inflate(R.layout.message_badge, itemView1, true);



        notifbadge=findViewById(R.id.notificationsbadge);
        messagebadge=findViewById(R.id.messagesbadge);



        newtransaction=findViewById(R.id.btnNewTrans);
        gotosettings=findViewById(R.id.gotosettings);
        txtwelcome=findViewById(R.id.txtWelcome);

        belloff=findViewById(R.id.belloff);
        bellon=findViewById(R.id.bellon);

        noresultimage=findViewById(R.id.noresultimage);
        nointernet=findViewById(R.id.nointernet);
        noresulttext=findViewById(R.id.noresulttext);
        nointernettext=findViewById(R.id.nointernettext);
        loading=findViewById(R.id.loadingdots);
        viewall=findViewById(R.id.viewall);
        alltransactions=findViewById(R.id.allTransactions);

        totalcost=findViewById(R.id.totalcost);
        totaltransactions=findViewById(R.id.totaltransactions);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        listItemsnotif = new ArrayList<>();
        listItemsmessage = new ArrayList<>();

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

                String input = "";

                Intent serviceIntent = new Intent(MainActivity.this, PersistService.class);
                serviceIntent.putExtra("inputExtra", input);

                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);

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
                Intent it = new Intent(MainActivity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();

            }
        });

     //load the total cost
        loadTotalCost();

       //load the totaltransactions
        loadTotalTransactions();

       //load transactions data
        loadRecyclerViewData();

        //LOAD BADGE ICONS FOR MESSAGES AND NOTIFICATIONS
        loadRecyclerViewDataNotifications();
        loadRecyclerViewDataMessages();

        txtRegId =  findViewById(R.id.txt_reg_id);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                }
            }
        };

        displayFirebaseRegId();

         UpdateRegid();

    }

    @Override
    public void onBackPressed() {
    saveListData();

    }



    private void logoutUser() {
        session.setLogin(false);
        Intent it = new Intent(MainActivity.this, Activity_Login.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }
    private void loadTotalCost(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_totalcost.php?user_id="+user_id+"";


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
      //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                TotalCostItem item=new TotalCostItem(
                                        o.getString("cost")
                                );




                               totalcost.setText("KSh "+o.getString("cost"));



                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyerror) {





                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void loadTotalTransactions(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_totaltransactions.php?user_id="+user_id+"";


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                TotalTransactionsItem item=new TotalTransactionsItem(
                                        o.getString("transactions")
                                );




                                totaltransactions.setText(o.getString("transactions"));



                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyerror) {





                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void loadRecyclerViewData(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_transactionsformain.php?user_id="+user_id+"";


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

                        loading.setVisibility(View.INVISIBLE);
                        nointernet.setVisibility(View.VISIBLE);
                        nointernettext.setVisibility(View.VISIBLE);



                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void loadRecyclerViewDataNotifications(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading data....");
//        progressDialog.show();

        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_notificationsbadge.php?user_id="+user_id+"";

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
                                NotificationListItem item=new NotificationListItem(
                                        o.getString("id"),
                                        o.getString("trans_id"),
                                        o.getString("agent_refno"),
                                        o.getString("agent_name"),
                                        o.getString("type"),
                                        o.getString("message"),
                                        o.getString("status"),
                                        o.getString("time")

                                );

                                //show recycler view and hhide no rsult
                                recyclerView.setVisibility(View.VISIBLE);
                                noresultimage.setVisibility(View.GONE);
                                noresulttext.setVisibility(View.GONE);

                                listItemsnotif.add(item);

                                if(o.getString("status").equals("Unread")){

                                  int lis=listItemsnotif.size();
                                   notifbadge.setVisibility(View.VISIBLE);
                                    notifbadge.setText(Integer.toString(lis));
                                }

                            }



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

    private void loadRecyclerViewDataMessages(){
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");


        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_messagesbadge.php?user_id="+user_id+"";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


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


                                listItemsmessage.add(item);



                                    int lis=listItemsmessage.size();
                                    messagebadge.setVisibility(View.VISIBLE);
                                    messagebadge.setText(Integer.toString(lis));




                            }




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

    private void saveListData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listItems);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadListData() {



        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);

        Type type = new TypeToken<ArrayList<ListItem>>() {}.getType();
         listItems = gson.fromJson(json, type);

       if (listItems == null) {
           listItems= new ArrayList<>();
        }
        else{

        }
    }




    // Fetches reg id from shared preferences
    // and displays on the screen

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        //Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            txtRegId.setText(regId);
           // Toast.makeText(MainActivity.this, regId, Toast.LENGTH_LONG).show();
        }
        else {
            //Toast.makeText(MainActivity.this, "Not Received", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    public void UpdateRegid(){

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
        com.ekaratasi.ApiServiceUpdateRegid service = retrofit.create(com.ekaratasi.ApiServiceUpdateRegid.class);
        UpdateRegid ur = new UpdateRegid();
        ur.setId(user_id);
        ur.setRegid(txtRegId.getText().toString());


        Call<UpdateRegid> call = service.insertRegidData(ur.getId(),ur.getRegid());

        call.enqueue(new Callback<UpdateRegid>() {
            @Override
            public void onResponse(Call<UpdateRegid> call, retrofit2.Response<UpdateRegid> response) {


            }

            @Override
            public void onFailure(Call<UpdateRegid> call, Throwable t) {

            }


        });

    }

    public void startService(View v) {
               String input = "";

        Intent serviceIntent = new Intent(this, PersistService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);

        belloff.setVisibility(View.VISIBLE);
        bellon.setVisibility(View.GONE);

    }


    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, PersistService.class);
        stopService(serviceIntent);

        bellon.setVisibility(View.VISIBLE);
        belloff.setVisibility(View.GONE);
    }


}
