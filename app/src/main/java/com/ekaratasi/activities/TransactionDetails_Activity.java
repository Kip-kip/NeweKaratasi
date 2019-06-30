package com.ekaratasi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ekaratasi.R;

public class TransactionDetails_Activity extends AppCompatActivity{
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        txt=findViewById(R.id.ello);

        String transId = getIntent().getStringExtra("TRANS_ID");
        txt.setText(transId);
    }
}
