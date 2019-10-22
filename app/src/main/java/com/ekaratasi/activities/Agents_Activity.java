package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.ekaratasi.adapter.AgentsAdapter;
import com.ekaratasi.adapter.MessageAdapter;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.app.Config;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.model.AgentListItem;
import com.ekaratasi.model.ListItem;
import com.ekaratasi.model.MessageListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Agents_Activity extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AgentListItem> listItems;

    //Declaring an Spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> locations;

    //JSON Array
    private JSONArray result;

    View loading;
    ImageView noresultimage,nointernet;
    TextView noresulttext,nointernettext;
    FloatingActionButton fab;
    private SQLiteHandler db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(Agents_Activity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_transactions:
                    Intent itt = new Intent(Agents_Activity.this, Transactions_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();


                    return true;

                case R.id.navigation_notifications:
                    Intent ittt= new Intent(Agents_Activity.this, Notification_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:
                    Intent itttt = new Intent(Agents_Activity.this, Message_Activity.class);
                    startActivity(itttt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_agents:
                    Intent ittttt = new Intent(Agents_Activity.this, Agents_Activity.class);
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
        setContentView(R.layout.activity_agents);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        //Initializing the ArrayList
       locations = new ArrayList<String>();

        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.location);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinner.setOnItemSelectedListener(this);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);


        loading=findViewById(R.id.loadingdots);
        noresultimage=findViewById(R.id.noresultimage);
        noresulttext=findViewById(R.id.noresulttext);
        nointernet=findViewById(R.id.nointernet);
        nointernettext=findViewById(R.id.nointernettext);


        recyclerView =findViewById(R.id.recyclerViewA);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();





         getData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItemText = (String) spinner.getItemAtPosition(position);
                // Notify the selected item text
                listItems.clear();

                loadRecyclerViewData(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the locations from the JSON Array
                            getLocations(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    private void getLocations(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                locations.add(json.getString(Config.TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(Agents_Activity.this, android.R.layout.simple_spinner_dropdown_item, locations));
    }

    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item

    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void loadRecyclerViewData(String si){

        loading.setVisibility(View.VISIBLE);

        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_agents.php?loc="+si+"";

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
                                AgentListItem item=new AgentListItem(
                                        o.getString("agent_name"),
                                        o.getString("agent_refno"),
                                        o.getString("phone"),
                                        o.getString("location"),
                                        o.getString("image"),
                                        o.getString("prices"),
                                        o.getString("offers"),
                                        o.getString("reviews")


                                );

                                //show recycler view and hhide no rsult
                                recyclerView.setVisibility(View.VISIBLE);
                                noresultimage.setVisibility(View.GONE);
                                noresulttext.setVisibility(View.GONE);


                                listItems.add(item);



                            }

                            adapter=new AgentsAdapter(listItems,getApplicationContext());
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
