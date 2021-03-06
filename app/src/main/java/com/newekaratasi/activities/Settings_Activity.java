package com.newekaratasi.activities;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newekaratasi.MainActivity;
import com.newekaratasi.POJO.SendTalk;
import com.newekaratasi.R;
import com.newekaratasi.helper.SQLiteHandler;
import com.newekaratasi.helper.SessionManager;
import com.newekaratasi.model.PPItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Settings_Activity extends AppCompatActivity {

    TextView logout,txtname,txtphone;
    Button editProfile,how;
    Button changePin,share,talktous;
    ImageView back,profilephoto;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        logout = findViewById(R.id.txtLogout);
        editProfile=findViewById(R.id.editProfile);
        changePin=findViewById(R.id.changePin);
        back=findViewById(R.id.back);
        talktous=findViewById(R.id.TalkToUs);
        how=findViewById(R.id.How);
        share=findViewById(R.id.share);
        profilephoto=findViewById(R.id.profilephotosettings);

        txtphone=findViewById(R.id.textMobileNumber);
        txtname=findViewById(R.id.textName);


        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("username");
        String phone = user.get("phone");

        txtphone.setText(phone);
        txtname.setText(name);

        // session manager
        session = new SessionManager(getApplicationContext());

        //load profile photo
        loadProfilePhoto();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Settings_Activity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout.setTextColor(Color.LTGRAY);
                //end session
                session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(Settings_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });



        //TO EDIT PROFILE ACTIVITY
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Settings_Activity.this, EditProfile_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();

            }
        });


        //TO CHANGE PIN
        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Settings_Activity.this, ChangePin_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);

                finish();
            }
        });

        //HOW TO USE
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Settings_Activity.this,HowToUse_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);

                finish();

            }
        });


        //TALK TO US
        talktous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showDialogTalkToUs();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="Download the eKaratasi app on google play store: https://play.google.com/store/apps/details?id=com.ekaratasi";
                String shareSub="Download the eKaratasi app on google play store: https://play.google.com/store/apps/details?id=com.ekaratasi";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share via"));

            }
        });

    }



    private void showDialogTalkToUs() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_talk);
        dialog.setCancelable(true);

        final EditText et_post = dialog.findViewById(R.id.et_post);

        ((Button) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send message



                //get the user_id
                // SqLite database handler
                db = new SQLiteHandler(getApplicationContext());

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();
                String phone = user.get("phone");

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ekaratasikenya.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                com.newekaratasi.ApiServiceSendTalk service = retrofit.create(com.newekaratasi.ApiServiceSendTalk.class);
                SendTalk sendtalk = new SendTalk();

                sendtalk.setPhone(phone);
                sendtalk.setText(et_post.getText().toString());

                Call<SendTalk> call = service.insertTalkData(sendtalk.getPhone(), sendtalk.getText());

                call.enqueue(new Callback<SendTalk>() {
                    @Override
                    public void onResponse(Call<SendTalk> call, retrofit2.Response<SendTalk> response) {
                        SendTalk tuongee=response.body();
                        String ongeleshwa=tuongee.getError_msg();
                        Integer num =Integer.parseInt(tuongee.getError());

                        if(num==0){
                            Toast.makeText(Settings_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();
                        }
                        else {


                            Toast.makeText(Settings_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<SendTalk> call, Throwable t) {

                    }


                });



            }
        });
        dialog.show();
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


    public void onBackPressed(){
        Intent it = new Intent(Settings_Activity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

}
