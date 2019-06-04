package com.ekaratasi.activities;

import android.content.Intent;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.ekaratasi.MainActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.ekaratasi.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;

import java.util.Locale;

public class Activity_Login extends AppCompatActivity {

Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Activity_Login.this,MainActivity.class);
                startActivity(it);
            }
        });

//        KenBurnsView kbv = findViewById(R.id.bg);
//        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//
//            }
//            @Override
//            public void onTransitionEnd(Transition transition) {
//
//            }
//        });
//
//        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
//        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000, ACCELERATE_DECELERATE);
////duration = 10000ms = 10s and interpolator = ACCELERATE_DECELERATE
//        kbv.setTransitionGenerator(generator); //
        /*SET CUSTOM FONT*/
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/PTM55FT.ttf");
//        textView.setTypeface(typeface);



    }
}
