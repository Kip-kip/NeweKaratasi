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

import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.model.MessageListItem;
import com.ekaratasi.model.NotificationListItem;

public class NotificationItem_Activity extends AppCompatActivity {
    TextView notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_item);


        NotificationListItem listItem = (NotificationListItem) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {
            notification = (TextView) findViewById(R.id.notification);


            notification.setText(listItem.getMessage());




        }




    }


}
