package com.ekaratasi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.ekaratasi.R;
import com.ekaratasi.model.ListItem;

public class TransactionItem_Activity extends AppCompatActivity {
    TextView material,cost;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_transactions:



                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_invoice:
                    Intent it = new Intent(TransactionItem_Activity.this, InvoiceItem_Activity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();

                    return true;

                case R.id.navigation_messages:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);

        ListItem listItem = (ListItem) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {
           material = (TextView) findViewById(R.id.material);
            cost = (TextView) findViewById(R.id.cost);


           cost.setText(listItem.getPrice());
            material.setText(listItem.getName());




        }

    }


}
