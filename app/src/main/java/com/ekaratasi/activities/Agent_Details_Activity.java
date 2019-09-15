package com.ekaratasi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.R;
import com.ekaratasi.model.AgentListItem;
import com.squareup.picasso.Picasso;

/**
 * Created by Cyrus on 4/6/2018.
 */

public class Agent_Details_Activity extends AppCompatActivity {

    TextView location,agentno,prices,offers,phone,cyberagent;
    ImageView inimage,back;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_details);
        back =  findViewById(R.id.back);
        AgentListItem listItem = (AgentListItem ) getIntent().getExtras().getSerializable("DETAIL");

        if (listItem != null) {
            cyberagent = (TextView) findViewById(R.id.cyberagent);
            location = (TextView) findViewById(R.id.location);
            agentno = (TextView) findViewById(R.id.agentno);
            prices=(TextView) findViewById(R.id.prices);
            phone=(TextView) findViewById(R.id.phone);
            offers=(TextView) findViewById(R.id.offers);

            inimage = (ImageView) findViewById(R.id.insideimage);

            cyberagent.setText(listItem.getAgent_name());

            location.setText("Location: "+listItem.getLocation());
            agentno.setText("Agent No: "+listItem.getAgent_refno());
            phone.setText("Phone No: "+listItem.getPhone());
            prices.setText(listItem.getPrices());
            offers.setText(listItem.getOffers());

            Picasso.with(context)
                    .load(listItem.getImage())
                    .resize(635, 415)
                    .into(inimage);



        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Agent_Details_Activity.this, Agents_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });



    }
}