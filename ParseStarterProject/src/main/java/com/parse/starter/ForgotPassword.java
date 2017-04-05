package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class ForgotPassword extends AppCompatActivity {

    Intent memberLoginIntent;
    EditText emailId, generatedCode;
    Button validationCodeButton;

    private static boolean verifyCodeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // gets intent from login activity
        memberLoginIntent = getIntent();

        // inflates the widgets
        emailId = (EditText) findViewById(R.id.userIdEditText);
        generatedCode = (EditText) findViewById(R.id.generatedCode);
        validationCodeButton = (Button) findViewById(R.id.validationCodeButton);
    }

    // this function will be called when send code button is clicked
    public void sendCode(View view) {

        if(emailId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email Id field cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            codeVerificationChecker();
        }

        // checks if the button is clicked
        Log.i("Info", "Send Code clicked");
    }

    private void codeVerificationChecker() {

        if (!verifyCodeActive) {

            generatedCode.setVisibility(View.VISIBLE);

            Random randomCodeGenerator;
            randomCodeGenerator = new Random();

            long code = randomCodeGenerator.nextInt(9000) + 1000;
            validationCodeButton.setText(R.string.validationCodeButtonTextChanged);

            // update the code to the database

            Log.i("Info", "Send Code");

            verifyCodeActive = true;

        } else {


            // write the code to verify the code from the database and then move to the reset password screen

            Log.i("Info", "Verify Code");

            verifyCodeActive = false;
        }
    }
}
