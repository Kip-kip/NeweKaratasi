package com.ekaratasi.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ekaratasi.ApiServicePay;
import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.SendMessage;
import com.ekaratasi.POJO.UserPay;
import com.ekaratasi.R;
import com.ekaratasi.adapter.TransactionsAdapter;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.model.ListItem;
import com.ekaratasi.service.PersistService;
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
    ImageView toinvoice,back;
    ViewFlipper myflipper;
    Button pay,decline;
    LinearLayout progressunseen,progresspending,progresscompleted;
    private SQLiteHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);

        toinvoice= (ImageView) findViewById(R.id.toInvoice);
        pay=findViewById(R.id.btnPay);
        decline=findViewById(R.id.btnDecline);
        myflipper = (ViewFlipper) findViewById(R.id.flipper);
        progressunseen=findViewById(R.id.progressunseen);
        progresspending=findViewById(R.id.progresspending);
        progresscompleted=findViewById(R.id.progresscompleted);
        back=findViewById(R.id.backk);


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


        //FLIP BACKWARDS
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showPrevious();
            }
        });



                      //PAYMENT----GO AHEAD WITH PAY ----initiate STK push
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogInitiatePayment();

             stkPush();


            }
        });


        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               showDialogDispute();

                     }
        });

        //hide invoice if status is not pending
        String progressstatus=progress.getText().toString();

        if(progressstatus.equals("Unseen")){
       progressunseen.setVisibility(View.VISIBLE);

        }
        else if(progressstatus.equals("Pending")){
            progresspending.setVisibility(View.VISIBLE);
            toinvoice.setVisibility(View.VISIBLE);
        }
        else if(progressstatus.equals("Completed")){
            progresscompleted.setVisibility(View.VISIBLE);
        }
        else{

        }



    }


    /*FUNCTION INSERTING INTO PAYMENT_RECORD TABLE*/
    void stkPush() {
        /*SAVE PHONE */
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String phone = user.get("phone");
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


    private void showDialogDispute() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_dispute);
        dialog.setCancelable(true);

        final EditText et_post = dialog.findViewById(R.id.et_post);

        final TextView agent_disputed = dialog.findViewById(R.id.agent_disputed);
        agent_disputed.setText(agent.getText().toString());

        ((Button) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send message



                //get the user_id
                // SqLite database handler
                db = new SQLiteHandler(getApplicationContext());

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();
                String user_id = user.get("uid");

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ekaratasikenya.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                com.ekaratasi.ApiServiceSendMessage service = retrofit.create(com.ekaratasi.ApiServiceSendMessage.class);
                SendMessage sendmessage = new SendMessage();
                sendmessage.setAgentt(agent.getText().toString());
                sendmessage.setCustomer(user_id);
                sendmessage.setSender("CUST");
                sendmessage.setReceiver("AGE");
                sendmessage.setText(et_post.getText().toString());

                Call<SendMessage> call = service.insertMessageData(sendmessage.getAgentt(), sendmessage.getCustomer(), sendmessage.getSender(),sendmessage.getReceiver(), sendmessage.getText());

                call.enqueue(new Callback<SendMessage>() {
                    @Override
                    public void onResponse(Call<SendMessage> call, retrofit2.Response<SendMessage> response) {
                        SendMessage tuongee=response.body();
                        String ongeleshwa=tuongee.getError_msg();
                        Integer num =Integer.parseInt(tuongee.getError());

                        if(num==0){
                            Toast.makeText(TransactionItem_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();
                        }
                        else {


                            Toast.makeText(TransactionItem_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<SendMessage> call, Throwable t) {

                    }


                });



            }
        });
        dialog.show();
    }


    private void showDialogInitiatePayment() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_image);
        dialog.setCancelable(true);
        dialog.show();
    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(TransactionItem_Activity.this, Transactions_Activity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }




}
