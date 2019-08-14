package com.ekaratasi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ekaratasi.R;

public class ChangePin_Activity extends AppCompatActivity {

ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpin);

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ChangePin_Activity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
                finish();

            }
        });

    }

    public void onBackPressed(){
        Intent it = new Intent(ChangePin_Activity.this, Settings_Activity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

}
