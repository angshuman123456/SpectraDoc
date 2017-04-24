package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.emailsAndPasswordRecovery.PasswordRecovery;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {

    Intent memberLoginIntent;
    EditText emailId, generatedCode;
    Button validationCodeButton;

    private static boolean verifyCodeActive = false;
    long code;

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
    // to check valid email address or empty email

    public void sendCode(View view) {
        if(emailId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            ParseQuery<ParseUser> emailQuery = ParseUser.getQuery();
            emailQuery.whereEqualTo("email", emailId.getText().toString());
            emailQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null && objects.size() > 0) {
                        codeVerificationChecker();
                    } else {
                        Toast.makeText(ForgotPassword.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void codeVerificationChecker() {

        if (!verifyCodeActive) {

            generatedCode.setVisibility(View.VISIBLE);

//            Random randomCodeGenerator;
//            randomCodeGenerator = new Random();
//
//            long code = randomCodeGenerator.nextInt(9000) + 1000;

            PasswordRecovery recovery = new PasswordRecovery(this, emailId.getText().toString());
            recovery.sendVerificationCode();
            code = recovery.getCode();

            validationCodeButton.setText(R.string.validationCodeButtonTextChanged);


            // email code is yet to be written

            Log.i("Info", "Send Code");

            verifyCodeActive = true;

        } else {


            // write the code to verify the code and then move to the reset password screen
            if(generatedCode.getText().toString().equals(String.valueOf(code))) {
                // move to reset password activity
            } else {
                Toast.makeText(this, "Code does not match", Toast.LENGTH_SHORT).show();
            }

            verifyCodeActive = false;
        }
    }
}
