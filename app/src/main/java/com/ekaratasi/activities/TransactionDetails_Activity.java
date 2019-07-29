package com.ekaratasi.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.ConfirmAgent;
import com.ekaratasi.POJO.MainData;
import com.ekaratasi.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionDetails_Activity extends AppCompatActivity{
TextView txt;
EditText copies,agent,instructions;
Spinner material,bindcolor,bindoption;
    Button SubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        txt=findViewById(R.id.ello);

        copies = (EditText) findViewById(R.id.copies);
        agent = (EditText) findViewById(R.id.agent);
        material = (Spinner) findViewById(R.id.material);
        bindcolor=(Spinner) findViewById(R.id.bindcolor);
        bindoption=(Spinner) findViewById(R.id.bindoption);
        instructions=(EditText) findViewById(R.id.instructions);
        String transId = getIntent().getStringExtra("TRANS_ID");
        txt.setText(transId);


        SubmitButton = (Button) findViewById(R.id.btnSubmit);

        // Adding click listener toSubmit button.
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               ConfirmAgent();

            }
        });


    }



    //confirm agent
    public void ConfirmAgent(){

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiServiceConfirm service = retrofit.create(com.ekaratasi.ApiServiceConfirm.class);
        ConfirmAgent confirmagent = new ConfirmAgent();
        confirmagent.setAgentrefno(agent.getText().toString());

        Call<ConfirmAgent> call = service.insertConfirmData(confirmagent.getAgentrefno());

        call.enqueue(new Callback<ConfirmAgent>() {
            @Override
            public void onResponse(Call<ConfirmAgent> call, Response<ConfirmAgent> response) {
                ConfirmAgent tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                     showDialogAgentAvailable(ongeleshwa);

                }
                else{
                    showDialogAgentAbsent();
                }
            }

            @Override
            public void onFailure(Call<ConfirmAgent> call, Throwable t) {

            }


        });
    }

     //save data

    public void SaveData(){

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiService service = retrofit.create(com.ekaratasi.ApiService.class);
        MainData maindata = new MainData();
        maindata.setMaterial(material.getSelectedItem().toString());
        maindata.setCopies(copies.getText().toString());
        maindata.setBindoption(bindoption.getSelectedItem().toString());
        maindata.setAgent(agent.getText().toString());
        maindata.setBindcolor(bindcolor.getSelectedItem().toString());
        maindata.setInstructions(instructions.getText().toString());
        Call<MainData> call = service.insertMainData(maindata.getMaterial(), maindata.getCopies(), maindata.getBindoption(), maindata.getBindcolor(),maindata.getAgent(),maindata.getInstructions());

        call.enqueue(new Callback<MainData>() {
            @Override
            public void onResponse(Call<MainData> call, Response<MainData> response) {
                MainData tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    showDialogSuccessfulTransaction();

                }
                else{
                    showDialogFailedTransaction();
                }
            }

            @Override
            public void onFailure(Call<MainData> call, Throwable t) {

            }


        });

    }


    public void showDialogAgentAvailable(String ong) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_agentavailable);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView messageView = (TextView)dialog.findViewById(R.id.passmes);

        messageView.setText(ong);

        //OKAY PROCEED
        ((Button) dialog.findViewById(R.id.btnP)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SaveData();
            }
        });

        //CANCEL
        ((Button) dialog.findViewById(R.id.btnC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showDialogAgentAbsent() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_agentabsent);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.btnCrossC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogSuccessfulTransaction() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_success_transaction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.btnSucdone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent it = new Intent(TransactionDetails_Activity.this,Transactions_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                finish();
            }
        });

        dialog.show();
    }

    private void showDialogFailedTransaction() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_failure_transaction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.btnFailDone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent it = new Intent(TransactionDetails_Activity.this, TransactionDetails_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });

        dialog.show();
    }

}
