package com.ekaratasi.activities;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.ReadMessage;
import com.ekaratasi.POJO.ReadNotification;
import com.ekaratasi.POJO.SendMessage;
import com.ekaratasi.R;
import com.ekaratasi.adapter.ChatAdapter;
import com.ekaratasi.adapter.MessageAdapter;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.model.ChatListItem;
import com.ekaratasi.model.ListItem;
import com.ekaratasi.model.MessageListItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageItem_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ChatListItem> listItems;
    private SQLiteHandler db;


    View loading;
TextView textViewMain,agent_refno,nointernettext;
EditText text,insertagent;
ImageView sendbtn,nointernet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_item);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        loading=findViewById(R.id.loadingdots);
        textViewMain= findViewById(R.id.textViewMain);
        agent_refno=findViewById(R.id.agent_refno);
        sendbtn=findViewById(R.id.sendBtn);
        text=findViewById(R.id.textmessage);
        insertagent=findViewById(R.id.insertagent);


        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nointernet=findViewById(R.id.nointernet);
        nointernettext=findViewById(R.id.nointernettext);


        listItems = new ArrayList<>();

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        Intent intent = getIntent();
        String indic= intent.getStringExtra("INDIC");


        if(indic.equals("CREATE")) {


            insertagent.setVisibility(View.VISIBLE);
            loading.setVisibility(View.INVISIBLE);

        }
        else{
            final MessageListItem messagelistItem = (MessageListItem) getIntent().getExtras().getSerializable("DETAIL");

            if (messagelistItem != null) {
                textViewMain.setText(messagelistItem.getAgent_name());
                agent_refno.setText(messagelistItem.getAgentt());
            }

            loadRecyclerViewData();

        }

        // Adding click listener Send Message button.
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = getIntent();
                String indic= intent.getStringExtra("INDIC");
                if(indic.equals("CREATE")) {
                    //set agent_refno from insert agent edit text
                   agent_refno.setText(insertagent.getText().toString());
                }
                else{

                }

                SendMessage();
               //clear edit text
                text.setText("");
            }
        });




        ReadMessage();

    }


    private void loadRecyclerViewData(){

        loading.setVisibility(View.VISIBLE);
// SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

        String agent= agent_refno.getText().toString();

//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading data....");
//        progressDialog.show();

        String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_message_item.php?user_id="+user_id+"&&agent="+agent;
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
                                ChatListItem item=new ChatListItem(
                                        o.getString("agent"),
                                        o.getString("customer"),
                                        o.getString("sender"),
                                        o.getString("receiver"),
                                        o.getString("text"),
                                        o.getString("time")

                                );


                                listItems.add(item);



                            }

                            adapter=new ChatAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(listItems.size() - 1);



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


    public void SendMessage(){

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
        com.ekaratasi.ApiServiceSendMessage service = retrofit.create(com.ekaratasi.ApiServiceSendMessage.class);
        SendMessage sendmessage = new SendMessage();
        sendmessage.setAgentt(agent_refno.getText().toString());
        sendmessage.setCustomer(user_id);
        sendmessage.setSender("CUST");
        sendmessage.setReceiver("AGE");
        sendmessage.setText(text.getText().toString());

        Call<SendMessage> call = service.insertMessageData(sendmessage.getAgentt(), sendmessage.getCustomer(), sendmessage.getSender(),sendmessage.getReceiver(), sendmessage.getText());

        call.enqueue(new Callback<SendMessage>() {
            @Override
            public void onResponse(Call<SendMessage> call, retrofit2.Response<SendMessage> response) {
                SendMessage tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();
                Integer num =Integer.parseInt(tuongee.getError());

                if(num==0){
                    Toast.makeText(MessageItem_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();
                }
                else {


                    Intent intent = getIntent();
                    String indic = intent.getStringExtra("INDIC");


                    if (indic.equals("CREATE")) {


                        loadRecyclerViewData();
                        Intent it = new Intent(MessageItem_Activity.this, Message_Activity.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                            finish();

                    } else {

                        listItems.clear();
                        adapter.notifyDataSetChanged();
                        hideKeyboard(MessageItem_Activity.this);
                        loadRecyclerViewData();

                    }

                }
            }

            @Override
            public void onFailure(Call<SendMessage> call, Throwable t) {

            }


        });

    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void ReadMessage(){

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
        com.ekaratasi.ApiServiceReadMessage service = retrofit.create(com.ekaratasi.ApiServiceReadMessage.class);
        ReadMessage readmessage = new ReadMessage();
        readmessage.setAgentt(agent_refno.getText().toString());
        readmessage.setCustomer(user_id);
        Call<ReadMessage> call = service.insertReadMessageData(readmessage.getAgentt(), readmessage.getCustomer());

        call.enqueue(new Callback<ReadMessage>() {
            @Override
            public void onResponse(Call<ReadMessage> call, retrofit2.Response<ReadMessage> response) {
                ReadMessage tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();


            }

            @Override
            public void onFailure(Call<ReadMessage> call, Throwable t) {

            }


        });


    }

}
