package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.POJO.MainData;
import com.ekaratasi.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

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
                            Intent it = new Intent(TransactionDetails_Activity.this, Activity_Transaction_Success.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                            finish();
                        }
                        else{
                            Intent it = new Intent(TransactionDetails_Activity.this, Activity_Transaction_Success.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<MainData> call, Throwable t) {

                    }


                });

            }
        });


    }




}
