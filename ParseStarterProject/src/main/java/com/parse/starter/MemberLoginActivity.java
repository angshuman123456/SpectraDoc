package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MemberLoginActivity extends AppCompatActivity {

    Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);

        mainActivityIntent = getIntent();
    }

    public void login(View view) {
        Log.i("Info", "Login clicked");
    }

    public void forgotPassword(View view) {
        Intent forgotPasswordIntent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(forgotPasswordIntent);
    }
}
