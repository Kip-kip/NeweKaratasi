package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ekaratasi.R;

public class InvoiceItem_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoiceitem);
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(InvoiceItem_Activity.this, TransactionItem_Activity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();

    }

}
