package com.ekaratasi.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.ConfirmAgent;
import com.ekaratasi.POJO.MainData;
import com.ekaratasi.R;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.service.PersistService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

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
    ImageView back;
    LinearLayout loadingview;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        copies = (EditText) findViewById(R.id.copies);
        agent = (EditText) findViewById(R.id.agent);
        material = (Spinner) findViewById(R.id.material);
        bindcolor=(Spinner) findViewById(R.id.bindcolor);
        bindoption=(Spinner) findViewById(R.id.bindoption);
        instructions=(EditText) findViewById(R.id.instructions);
        loadingview=findViewById(R.id.loading_view);
        SubmitButton = (Button) findViewById(R.id.btnSubmit);









        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TransactionDetails_Activity.this, PDFUpload_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });



//HIDE BIND COLOR WHEN BIND OPTION IS NOT SELECTED
        bindoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItemText = (String) bindoption.getItemAtPosition(position);
                // Notify the selected item text
                if(selectedItemText.equals("No")){
                    bindcolor.setVisibility(View.INVISIBLE);
                }
                else{
                    bindcolor.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });







        // Adding click listener toSubmit button.
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //VALIDATION

                if(agent.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Agent number cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if(material.getSelectedItem().equals("Select material")){

                        Toast.makeText(getApplicationContext(), "Please choose material", Toast.LENGTH_LONG).show();

                }
                else if(copies.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Please fill number of copies", Toast.LENGTH_LONG).show();

                }
                else if(bindoption.getSelectedItem().equals("Select bind option")){

                    Toast.makeText(getApplicationContext(), "Please choose bind option", Toast.LENGTH_LONG).show();

                }
                else{

                    //hide login button
                    SubmitButton.setVisibility(View.INVISIBLE);
                    //unhide loading view
                    loadingview.setVisibility(View.VISIBLE);

                    /**-START SERVICE FOR NOTIFICATIONS-**/
                    String input = "";

                    Intent serviceIntent = new Intent(TransactionDetails_Activity.this, PersistService.class);
                    serviceIntent.putExtra("inputExtra", input);

                    ContextCompat.startForegroundService(TransactionDetails_Activity.this, serviceIntent);
                    /**--**/

                    ConfirmAgent();

                }




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
        com.ekaratasi.ApiService service = retrofit.create(com.ekaratasi.ApiService.class);
        MainData maindata = new MainData();
        maindata.setUser_id(user_id);
        maindata.setMaterial(material.getSelectedItem().toString());
        maindata.setCopies(copies.getText().toString());
        maindata.setBindoption(bindoption.getSelectedItem().toString());
        maindata.setAgent(agent.getText().toString());
        maindata.setBindcolor(bindcolor.getSelectedItem().toString());
        maindata.setInstructions(instructions.getText().toString());
        maindata.setDocfile(getIntent().getStringExtra("PDF_REFNO"));
        Call<MainData> call = service.insertMainData(maindata.getUser_id(), maindata.getMaterial(), maindata.getCopies(), maindata.getBindoption(), maindata.getBindcolor(),maindata.getAgent(),maindata.getInstructions(),maindata.getDocfile());

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
                SubmitButton.setVisibility(View.VISIBLE);
                //unhide loading view
                loadingview.setVisibility(View.INVISIBLE);
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

                SubmitButton.setVisibility(View.VISIBLE);
                //unhide loading view
                loadingview.setVisibility(View.INVISIBLE);

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

        ((Button) dialog.findViewById(R.id.btnCrossC)).setOnClickListener(new View.OnClickListener() {
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
                Intent it = new Intent(TransactionDetails_Activity.this, PDFUpload_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });

        dialog.show();
    }

    public void onBackPressed() {
        Intent i = new Intent(TransactionDetails_Activity.this, PDFUpload_Activity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();



    }


}
