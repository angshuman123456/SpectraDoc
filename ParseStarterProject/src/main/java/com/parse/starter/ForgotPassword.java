package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class ForgotPassword extends AppCompatActivity {

    Intent memberLoginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        memberLoginIntent = getIntent();
    }

    public void sendCode(View view) {
        Log.i("Info", "Send Code clicked");
    }
}
