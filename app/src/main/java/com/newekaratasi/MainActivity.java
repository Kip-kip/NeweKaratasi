package com.newekaratasi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newekaratasi.POJO.CheckTransExistence;
import com.newekaratasi.POJO.UpdateRegid;
import com.newekaratasi.activities.Activity_Login;
import com.newekaratasi.activities.Agents_Activity;
import com.newekaratasi.activities.Message_Activity;
import com.newekaratasi.activities.Notification_Activity;
import com.newekaratasi.activities.PDFUpload_Activity;
import com.newekaratasi.activities.Settings_Activity;
import com.newekaratasi.activities.Transactions_Activity;
import com.newekaratasi.adapter.TransactionsAdapter;
import com.newekaratasi.app.Config;
import com.newekaratasi.helper.SQLiteHandler;
import com.newekaratasi.helper.SessionManager;
import com.newekaratasi.model.ListItem;
import com.newekaratasi.model.MessageListItem;
import com.newekaratasi.model.NotificationListItem;
import com.newekaratasi.model.PPItem;
import com.newekaratasi.model.TotalCostItem;
import com.newekaratasi.model.TotalTransactionsItem;
import com.newekaratasi.service.PersistService;
import com.newekaratasi.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ImageView gotosettings,noresultimage,nointernet,belloff,bellon,profilephoto;

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

                case R.id.navigation_agents:

                    Intent itttt = new Intent(MainActivity.this, Agents_Activity.class);
                    startActivity(itttt);
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
    private List<PPItem> listItemsprofilephoto;

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

        /**INSTATIATION**/

        notifbadge=findViewById(R.id.notificationsbadge);
        messagebadge=findViewById(R.id.messagesbadge);
        newtransaction=findViewById(R.id.btnNewTrans);
        gotosettings=findViewById(R.id.gotosettings);
        txtwelcome=findViewById(R.id.txtWelcome);
         belloff=findViewById(R.id.belloff);
        bellon=findViewById(R.id.bellon);
        profilephoto=findViewById(R.id.profilephoto);
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




       //set profilephoto



       //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


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

                gotosettings.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                        R.color.mygray));

                Intent it = new Intent(MainActivity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();

            }
        });

        //check if user has made any transactions yet
        checkTransExistence();

     //load the total cost
        loadTotalCost();

       //load the totaltransactions
        loadTotalTransactions();

        //load profile photo
        loadProfilePhoto();

       //load transactions data
//        loadRecyclerViewData();
        loadDummy();

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



        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

    }

    @Override
    public void onBackPressed() {


        finish();

    }



    /**log out function**/

    private void logoutUser() {
        session.setLogin(false);
        Intent it = new Intent(MainActivity.this, Activity_Login.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }


    /**loading the total cost of all transactions function**/

    private void loadTotalCost(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_totalcost.php?user_id="+user_id+"";


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



    /**loading total number of transaction function**/

    private void loadTotalTransactions(){
        totaltransactions.setText("2");

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_totaltransactions.php?user_id="+user_id+"";


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




                                totaltransactions.setText("2");



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


    /**loading profile picture**/

    private void loadProfilePhoto(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_profilephoto.php?user_id="+user_id+"";


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
                                PPItem item=new PPItem(
                                        o.getString("profilephoto")
                                );


                                  String ImgUrl=o.getString("profilephoto");

                                //check here to KITKAT or new version
                                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
                                    Picasso.with(getApplicationContext()).load(ImgUrl).fit().into(profilephoto);
                                }
                                else{

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





                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void loadDummy(){
        recyclerView.setVisibility(View.VISIBLE);
        ListItem item=new ListItem(
                "1123",
                "Mwaks Printing Centre",
                "70",
                "REF12355",
                "CUST1245",
                "+25490767583",
                "A4",
                "Blue",
                "Spiral",
                "2",
                "Please do it neatly",
                "Pending Payment",
                "Invoiced",
                "2021-04-07",
                "15",
                "5",
                "5",
                "10",
                "20",
                "50",
                "175",
                "17",
                ""

        );

        for(int x=0;x<=2;x++){
            listItems.add(item);
        }


        adapter=new TransactionsAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


    /**loading transactions oh the main activity function**/

    private void loadRecyclerViewData(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_transactionsformain.php?user_id="+user_id+"";


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // dismiss loading
                        loading.setVisibility(View.INVISIBLE);




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



//                                listItems.add(item);

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


                        //SHOW NO INTERNET
                        loading.setVisibility(View.INVISIBLE);
                        nointernet.setVisibility(View.VISIBLE);
                        nointernettext.setVisibility(View.VISIBLE);




                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    /**loading total number of notifications on the badge bottom navigation function**/

    private void loadRecyclerViewDataNotifications(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading data....");
//        progressDialog.show();

        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_notificationsbadge.php?user_id="+user_id+"";

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


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    /**loading total number of messages on the badge bottom navigation function**/

    private void loadRecyclerViewDataMessages(){
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");


        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_messagesbadge.php?user_id="+user_id+"";

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

                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void checkTransExistence() {
        /*SAVE PHONE */
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/" )
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiServiceTransExistence service = retrofit.create(ApiServiceTransExistence.class);
        CheckTransExistence ct = new CheckTransExistence();
        ct.setUser_id(user_id);
        Call<CheckTransExistence> call = service.insertTransExistence(ct.getUser_id());

        call.enqueue(new Callback<CheckTransExistence>() {
            @Override
            public void onResponse(Call<CheckTransExistence> call, retrofit2.Response<CheckTransExistence> response) {
                CheckTransExistence tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());

//if TRANSACTIONs are available hide NO RESULT IMAGE AND TEXT
                if(num==1){






                }
                else{

                    //show no result
                    noresulttext.setVisibility(View.VISIBLE);
                    noresultimage.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onFailure(Call<CheckTransExistence> call, Throwable t) {

            }


        });

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
        com.newekaratasi.ApiServiceUpdateRegid service = retrofit.create(com.newekaratasi.ApiServiceUpdateRegid.class);
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
