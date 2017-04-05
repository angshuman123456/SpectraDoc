package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    Intent memberLoginIntent;
    EditText emailId, generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // gets intent from login activity
        memberLoginIntent = getIntent();

        // inflates the widgets
        emailId = (EditText) findViewById(R.id.emailId);
        generatedCode = (EditText) findViewById(R.id.generatedCode);
    }

    // this function will be called when send code button is clicked
    public void sendCode(View view) {

        generatedCode.setVisibility(View.VISIBLE);
        

        Log.i("Info", "Send Code clicked");
    }
}
