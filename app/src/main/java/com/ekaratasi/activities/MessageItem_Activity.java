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
import com.ekaratasi.adapter.ChatAdapter;
import com.ekaratasi.adapter.MessageAdapter;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.model.ChatListItem;
import com.ekaratasi.model.ListItem;
import com.ekaratasi.model.MessageListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageItem_Activity extends AppCompatActivity {

    private static final String URL_DATA="https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_messages.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ChatListItem> listItems;

    View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_item);


        loading=findViewById(R.id.loadingdots);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
                                ChatListItem item=new ChatListItem(
                                        o.getString("sender"),
                                        o.getString("receiver"),
                                        o.getString("text"),
                                        o.getString("time")

                                );


                                listItems.add(item);



                            }

                            adapter=new ChatAdapter(listItems,getApplicationContext());
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
