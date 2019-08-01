package com.ekaratasi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ekaratasi.ApiServicePay;
import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.UserPay;
import com.ekaratasi.R;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.model.ListItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionItem_Activity extends AppCompatActivity {
    TextView agent,trans_refno,customer_refno,material,bind_color,bind_type,copies,instructions,payment,ccopies,
            invoice,progress,bw_pages,bw_cost,bw_total,c_pages,c_cost,c_total,total_pages,bind_cost,bind_total,ekaratasi_fee,total_cost,total_cost2;
    ImageView toinvoice;
    ViewFlipper myflipper;
    Button pay,decline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);


        toinvoice= (ImageView) findViewById(R.id.toInvoice);
        pay=findViewById(R.id.btnPay);
        decline=findViewById(R.id.btnDecline);
        myflipper = (ViewFlipper) findViewById(R.id.flipper);


        final ListItem listItem = (ListItem) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {

//            acquire from layout TRANSACTION.XML
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


            //acquire from layout INVOICE.XML
            bw_pages =  findViewById(R.id.bw_pages);
            bw_cost = findViewById(R.id.bw_cost);
            bw_total=findViewById(R.id.bw_total);
            c_pages =  findViewById(R.id.c_pages);
            c_cost =  findViewById(R.id.c_cost);
            c_total=findViewById(R.id.c_total);
            total_pages=findViewById(R.id.total_pages);
            bind_cost=findViewById(R.id.bind_cost);
            bind_total=findViewById(R.id.bind_total);
            ekaratasi_fee=findViewById(R.id.ekaratasi_fee);
            total_cost=findViewById(R.id.total_cost);
            total_cost2=findViewById(R.id.total_cost2);
            ccopies=findViewById(R.id.ccopies);



            //SET TEXT VIEWS TO VALUES ON TRANSACTION ACTIVITY
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



            //SET TEXT VIEWS TO VALUES ON INVOICE ACTIVITY
                  bw_pages.setText(listItem.getBw_pages());
                    bw_cost.setText(listItem.getBw_cost());
                    c_pages.setText(listItem.getC_pages());
                    c_cost.setText(listItem.getC_cost());
                   total_pages.setText(listItem.getTotal_pages());
                    bind_cost.setText(listItem.getBind_cost());
                    bind_total.setText(listItem.getBind_cost());
                   total_cost.setText(listItem.getTotal_cost());
                  total_cost2.setText(listItem.getTotal_cost());
                   ekaratasi_fee.setText((listItem.getEkaratasi_fee()));
                   ccopies.setText(listItem.getCopies());



//compute bw_total
            String bwpagesstring=listItem.getBw_pages();
            int bwpagesint=Integer.parseInt(bwpagesstring);
            String bwcoststring=listItem.getBw_cost();
            int bwcostint=Integer.parseInt(bwcoststring);

int bwtotalcost=bwpagesint * bwcostint;

            bw_total.setText(Integer.toString(bwtotalcost));
//compute c_total

            String cpagesstring=listItem.getC_pages();
            int cpagesint=Integer.parseInt(cpagesstring);
            String ccoststring=listItem.getC_cost();
            int ccostint=Integer.parseInt(ccoststring);

            int ctotalcost=cpagesint * ccostint;

            c_total.setText(Integer.toString(ctotalcost));




        }



        //TO INVOICE
        toinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showNext();
            }
        });





                      //PAYMENT----GO AHEAD WITH PAY ----initiate STK push
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             stkPush();
                Toast.makeText(TransactionItem_Activity.this, "Payment will be initiated in a moment. Hold still", Toast.LENGTH_LONG).show();
            }
        });


    }


    /*FUNCTION INSERTING INTO PAYMENT_RECORD TABLE*/
    void stkPush() {
        /*SAVE PHONE */
        // SqLite database handler
//        db = new SQLiteHandler(getApplicationContext());
//
//        // Fetching user details from sqlite
//        HashMap<String, String> user = db.getUserDetails();
        String phone = "0718700519"; //user.get("phone");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/" )
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiServicePay service = retrofit.create(ApiServicePay.class);
        UserPay userpay = new UserPay();
        userpay.setPhone(phone);
        userpay.setCash(total_cost.getText().toString());
        userpay.setTrans_refno(trans_refno.getText().toString());
        Call<UserPay> call = service.insertPaymentData(userpay.getPhone(), userpay.getCash(), userpay.getTrans_refno());

        call.enqueue(new Callback<UserPay>() {
            @Override
            public void onResponse(Call<UserPay> call, Response<UserPay> response) {


            }

            @Override
            public void onFailure(Call<UserPay> call, Throwable t) {

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
