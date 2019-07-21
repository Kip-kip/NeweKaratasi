package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.R;
import com.ekaratasi.model.ListItem;

public class InvoiceItem_Activity extends AppCompatActivity {
    TextView bw,bwpages,bwprice,bwtotal,refno,customer_refno,material,bind_color,bind_type,copies,instructions,payment,invoice,progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoiceitem);

        Intent intent = getIntent();
        String indic= intent.getStringExtra("bw");


//            bind_color = (TextView) findViewById(R.id.textViewBindColor);
//            bind_type = (TextView) findViewById(R.id.textViewBindType);
//            copies = (TextView) findViewById(R.id.textViewCopies);
//            instructions = (TextView) findViewById(R.id.textViewInst);
//            payment = (TextView) findViewById(R.id.textViewPayment);
//            invoice = (TextView) findViewById(R.id.textViewInvoice);
//            progress = (TextView) findViewById(R.id.textViewProgress);


            bw.setText(indic);
//            bwprice.setText(listItem.getTrans_refno());
//           bwpages.setText(listItem.getCustomer_refno());
//            bwtotal.setText(listItem.getMaterial());
//            bind_type.setText(listItem.getBind_type());
//            bind_color.setText(listItem.getBind_color());
//            copies.setText(listItem.getCopies());
//            instructions.setText(listItem.getInstructions());
//            payment.setText(listItem.getPayment_status());
//            invoice.setText(listItem.getInvoice_status());
//            progress.setText(listItem.getProgress());





    }

    @Override
    public void onBackPressed() {


//        Intent it = new Intent(InvoiceItem_Activity.this, TransactionItem_Activity.class);
//        startActivity(it);
//        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
//        finish();

    }

}
