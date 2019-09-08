package com.ekaratasi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ekaratasi.MainActivity;
import com.ekaratasi.R;

/**
 * Created by Cyrus on 9/27/2017.
 */

public class Splash_Activity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ImageView logo;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash_Activity.this, MainActivity.class);
                Splash_Activity.this.startActivity(mainIntent);
                Splash_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        /*animation*/

        logo = (ImageView) findViewById(R.id.imageView);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation);
        logo.startAnimation(startAnimation);

    }
}