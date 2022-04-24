package com.newekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newekaratasi.POJO.ReadNotification;
import com.newekaratasi.R;
import com.newekaratasi.model.NotificationListItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationItem_Activity extends AppCompatActivity {
    TextView notification,id;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_item);
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(NotificationItem_Activity.this, Notification_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });

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
        com.newekaratasi.ApiServiceReadNotif service = retrofit.create(com.newekaratasi.ApiServiceReadNotif.class);
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
