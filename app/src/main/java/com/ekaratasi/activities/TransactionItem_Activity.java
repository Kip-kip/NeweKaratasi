package com.ekaratasi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.model.ListItem;

import java.util.List;

public class TransactionItem_Activity extends AppCompatActivity {
    TextView agent,trans_refno,customer_refno,material,bind_color,bind_type,copies,instructions,payment,
            invoice,progress,bw_pages,bw_cost,c_pages,c_cost,total_pages,bind_cost,ekaratasi_fee,total_cost,total_cost2;
    ImageView toinvoice;
    ViewFlipper myflipper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);


        toinvoice= (ImageView) findViewById(R.id.toInvoice);
        myflipper = (ViewFlipper) findViewById(R.id.flipper);


        final ListItem listItem = (ListItem) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {
           agent = (TextView) findViewById(R.id.textViewAgent);
           trans_refno = (TextView) findViewById(R.id.textViewTransRefno);
            customer_refno = (TextView) findViewById(R.id.textViewCustRefno);
            material = (TextView) findViewById(R.id.textViewMaterial);
            bind_color = (TextView) findViewById(R.id.textViewBindColor);
            bind_type = (TextView) findViewById(R.id.textViewBindType);
            copies = (TextView) findViewById(R.id.textViewCopies);
            instructions = (TextView) findViewById(R.id.textViewInst);
            payment = (TextView) findViewById(R.id.textViewPayment);
            invoice = (TextView) findViewById(R.id.textViewInvoice);
            progress = (TextView) findViewById(R.id.textViewProgress);


            //Flipped
            bw_pages = (TextView) findViewById(R.id.bw_pages);
            bw_cost = (TextView) findViewById(R.id.bw_cost);
            c_pages = (TextView) findViewById(R.id.c_pages);
            c_cost = (TextView) findViewById(R.id.c_cost);
            total_pages=findViewById(R.id.total_pages);
            bind_cost=findViewById(R.id.bind_cost);
            ekaratasi_fee=findViewById(R.id.ekaratasi_fee);
            total_cost=findViewById(R.id.total_cost);
            total_cost2=findViewById(R.id.total_cost2);

            agent.setText(listItem.getAgent());
            trans_refno.setText(listItem.getTrans_refno());
            customer_refno.setText(listItem.getCustomer_refno());
            material.setText(listItem.getMaterial());
            bind_type.setText(listItem.getBind_type());
            bind_color.setText(listItem.getBind_color());
            copies.setText(listItem.getCopies());
            instructions.setText(listItem.getInstructions());
            payment.setText(listItem.getPayment_status());
            invoice.setText(listItem.getInvoice_status());
            progress.setText(listItem.getProgress());

            //Flipped
                  bw_pages.setText(listItem.getBw_pages());
                    bw_cost.setText(listItem.getBw_cost());
                    c_pages.setText(listItem.getC_pages());
                    c_cost.setText(listItem.getC_cost());
                   total_pages.setText(listItem.getTotal_pages());
                    bind_cost.setText(listItem.getBind_cost());
                   total_cost.setText(listItem.getTotal_cost());
            total_cost2.setText(listItem.getTotal_cost());
                   ekaratasi_fee.setText((listItem.getEkaratasi_fee()));





        }



        //TO INVOICE
        toinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showNext();
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(TransactionItem_Activity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }




}
