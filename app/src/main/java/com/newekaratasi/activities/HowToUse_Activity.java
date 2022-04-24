package com.newekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.newekaratasi.R;

public class HowToUse_Activity extends AppCompatActivity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtouse);
        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(HowToUse_Activity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();

            }
        });

    }
    public void onBackPressed(){
        Intent it = new Intent(HowToUse_Activity.this, Settings_Activity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();
    }

}