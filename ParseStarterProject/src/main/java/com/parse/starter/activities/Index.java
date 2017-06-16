package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.parse.starter.R;

public class Index extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        logo = (ImageView) findViewById(R.id.logo);

        Animation pulse = AnimationUtils.loadAnimation(Index.this, R.anim.pulse);
        logo.startAnimation(pulse);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent i = new Intent(getApplicationContext(), MemberLoginActivity.class);
                Index.this.startActivity(i);
                Index.this.finish();
            }
        }, 5000);


    }
}
