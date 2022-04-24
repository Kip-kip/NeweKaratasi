package com.newekaratasi.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.newekaratasi.ApiServiceCancelTrans;
import com.newekaratasi.ApiServiceCheckLeastTrans;
import com.newekaratasi.ApiServicePay;
import com.newekaratasi.POJO.CancelTrans;
import com.newekaratasi.POJO.CheckLeastTrans;
import com.newekaratasi.POJO.SendMessage;
import com.newekaratasi.POJO.UserPay;
import com.newekaratasi.R;
import com.newekaratasi.helper.SQLiteHandler;
import com.newekaratasi.model.ListItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionItem_Activity extends AppCompatActivity {
    TextView agent,trans_refno,customer_refno,material,bind_color,bind_type,copies,instructions,payment,ccopies,
            invoice,progress,bw_pages,bw_cost,bw_total,c_pages,c_cost,c_total,total_pages,bind_cost,bind_total,total_cost,total_cost2;
    ImageView toinvoice,back,backk,tocancel,viewpdf;
    ViewFlipper myflipper;
    Button pay,decline;
    LinearLayout progressunseen,progresspending,progresscompleted,progresscancelled;
    TextView doc_path;
    private SQLiteHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        toinvoice= (ImageView) findViewById(R.id.toInvoice);
        tocancel=findViewById(R.id.toCancel);
        viewpdf=findViewById(R.id.viewpdf);
        pay=findViewById(R.id.btnPay);
        decline=findViewById(R.id.btnDecline);
        myflipper = (ViewFlipper) findViewById(R.id.flipper);
        progressunseen=findViewById(R.id.progressunseen);
        progresspending=findViewById(R.id.progresspending);
        progresscompleted=findViewById(R.id.progresscompleted);
        progresscancelled=findViewById(R.id.progresscancelled);
        backk=findViewById(R.id.backk);
        back=findViewById(R.id.back);
        doc_path=findViewById(R.id.doc_path);


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
            doc_path.setText(listItem.getDoc_path());



            //SET TEXT VIEWS TO VALUES ON INVOICE ACTIVITY
            bw_pages.setText(listItem.getBw_pages());
            bw_cost.setText(listItem.getBw_cost());
            c_pages.setText(listItem.getC_pages());
            c_cost.setText(listItem.getC_cost());
            total_pages.setText(listItem.getTotal_pages());
            bind_cost.setText(listItem.getBind_cost());
            bind_total.setText(listItem.getBind_cost());
            total_cost.setText("KES "+listItem.getTotal_cost());
            total_cost2.setText("KES "+listItem.getTotal_cost());

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


        //VIEW PDF
        final String pdflink=doc_path.getText().toString();
        viewpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + pdflink), "text/html");
                startActivity(intent);
            }
        });





        //TO INVOICE
        toinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showNext();
            }
        });

        //CANCEL TRANSACTION
        tocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogConfirmCancel();

            }
        });

        //FLIP BACKWARDS
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myflipper.showPrevious();
            }
        });


        //FLIP BACKWARDS
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(TransactionItem_Activity.this, Transactions_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });


        //PAYMENT----GO AHEAD WITH PAY ----initiate STK push
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogInitiatePayment();

             checkLeastTrans();



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
        String paymentstatus=payment.getText().toString();
        String invoicestatus=invoice.getText().toString();

        if(progressstatus.equals("Unseen")){
            progressunseen.setVisibility(View.VISIBLE);

        }
        else if(progressstatus.equals("Completed")){
            progresscompleted.setVisibility(View.VISIBLE);
            tocancel.setVisibility(View.INVISIBLE);
        }
        else if(progressstatus.equals("Cancelled")){
            progresscancelled.setVisibility(View.VISIBLE);
            tocancel.setVisibility(View.INVISIBLE);
        }
        else{

        }


         if((progressstatus.equals("Pending"))&&(paymentstatus.equals("Not Paid")) && (invoicestatus.equals("Sent"))){

            toinvoice.setVisibility(View.VISIBLE);
        }


        if(paymentstatus.equals("Paid")){
            tocancel.setVisibility(View.INVISIBLE);
        }



    }


    public void checkLeastTrans() {
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
        ApiServiceCheckLeastTrans service = retrofit.create(ApiServiceCheckLeastTrans.class);
        CheckLeastTrans clt = new CheckLeastTrans();
        clt.setPhonenum(phone);
        clt.setTrans_refno(trans_refno.getText().toString());
        Call<CheckLeastTrans> call = service.insertLeastTransData(clt.getPhonenum(), clt.getTrans_refno());

        call.enqueue(new Callback<CheckLeastTrans>() {
            @Override
            public void onResponse(Call<CheckLeastTrans> call, Response<CheckLeastTrans> response) {
                CheckLeastTrans tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    stkPush();

                    //Toast.makeText(TransactionItem_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();

                }
                else{

                    showDialogFailedCheck();


                }

            }

            @Override
            public void onFailure(Call<CheckLeastTrans> call, Throwable t) {

            }


        });

    }


    /*FUNCTION INSERTING INTO PAYMENT_RECORD TABLE*/
    public void stkPush() {
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
        Call<UserPay> call = service.insertPaymentData(userpay.getPhone(), userpay.getCash());

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
                com.newekaratasi.ApiServiceSendMessage service = retrofit.create(com.newekaratasi.ApiServiceSendMessage.class);
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

    private void showDialogFailedCheck() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_failure_check);
        dialog.setCancelable(true);
        dialog.show();
    }


    public void cancelTrans(){

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/" )
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiServiceCancelTrans service = retrofit.create(ApiServiceCancelTrans.class);
        CancelTrans cl = new CancelTrans();

        cl.setTrans_refno(trans_refno.getText().toString());
        Call<CancelTrans> call = service.insertCancelData(cl.getTrans_refno());

        call.enqueue(new Callback<CancelTrans>() {
            @Override
            public void onResponse(Call<CancelTrans> call, Response<CancelTrans> response) {
                CancelTrans tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                   showDialogCancelSuccess();

                }
                else{




                }

            }

            @Override
            public void onFailure(Call<CancelTrans> call, Throwable t) {

            }


        });
    }

    private void showDialogCancelSuccess(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_success_cancel);
        dialog.setCancelable(true);


        dialog.show();
    }

    private void showDialogConfirmCancel(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_confirmcancel);
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.btno)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btyes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelTrans();


            }
        });
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
