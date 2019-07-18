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
import com.ekaratasi.model.ListItem;

public class TransactionItem_Activity extends AppCompatActivity {
    TextView agent,trans_refno,customer_refno,material,bind_color,bind_type,copies,instructions,payment,invoice,progress;
    ImageView toinvoice;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent it = new Intent(TransactionItem_Activity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_transactions:
                    Intent itt = new Intent(TransactionItem_Activity.this, TransactionItem_Activity.class);
                    startActivity(itt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();


                    return true;

                case R.id.navigation_notifications:
                    Intent ittt= new Intent(TransactionItem_Activity.this, Notification_Activity.class);
                    startActivity(ittt);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                    finish();
                    return true;

                case R.id.navigation_messages:
                    Intent itttt = new Intent(TransactionItem_Activity.this, Message_Activity.class);
                    startActivity(itttt);
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
        setContentView(R.layout.activity_transaction_item);


        toinvoice= (ImageView) findViewById(R.id.toInvoice);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setItemIconTintList(null);

        ListItem listItem = (ListItem) getIntent().getExtras().getSerializable("DETAIL");

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



        }



        //TO INVOICE
        toinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TransactionItem_Activity.this, InvoiceItem_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();
            }
        });


    }


}
