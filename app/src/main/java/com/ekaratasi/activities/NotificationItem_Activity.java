package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.EditProfile;
import com.ekaratasi.POJO.ReadNotification;
import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.model.MessageListItem;
import com.ekaratasi.model.NotificationListItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationItem_Activity extends AppCompatActivity {
    TextView notification,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_item);


        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        NotificationListItem listItem = (NotificationListItem) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {
            notification = (TextView) findViewById(R.id.notification);
            id = (TextView) findViewById(R.id.idd);

            notification.setText(listItem.getMessage());
            id.setText(listItem.getId());




        }

        ReadNotification();



    }


    public void ReadNotification(){

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiServiceReadNotif service = retrofit.create(com.ekaratasi.ApiServiceReadNotif.class);
        ReadNotification rn = new ReadNotification();
        rn.setId(id.getText().toString());


        Call<ReadNotification> call = service.insertReadNotifData(rn.getId());

        call.enqueue(new Callback<ReadNotification>() {
            @Override
            public void onResponse(Call<ReadNotification> call, Response<ReadNotification> response) {
                ReadNotification tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


            }

            @Override
            public void onFailure(Call<ReadNotification> call, Throwable t) {

            }


        });

    }


}
