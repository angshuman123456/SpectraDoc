package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MemberLoginActivity extends AppCompatActivity {

    Intent mainActivityIntent;
    Switch studentFacultySwitch;
    EditText loginUserId, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);

        mainActivityIntent = getIntent();

        loginUserId = (EditText) findViewById(R.id.loginUserId);
        loginPassword = (EditText) findViewById(R.id.loginPassword);

        studentFacultySwitch = (Switch) findViewById(R.id.student_faculty_switch);

        studentFacultySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    loginUserId.setHint("Department Id");
                } else {
                    loginUserId.setHint("Roll Number");
                }
            }
        });
    }

    public void login(View view) {

        /*
        * This method is called when login button is clicked.
        * perform the login operation here
         */


        if(loginPassword.getText().toString().isEmpty() ||
                loginUserId.getText().toString().isEmpty()) {

            Toast.makeText(this, "Username or password field cannot be empty", Toast.LENGTH_SHORT).show();
        }



        if(studentFacultySwitch.isChecked()) {

            // write the code for faculty login

        } else {

            // write the code for student login
        }

        Log.i("Info", "Login clicked");
    }

    public void forgotPassword(View view) {

        // this function is called whenever "Forgot password" text view is called

        Intent forgotPasswordIntent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(forgotPasswordIntent);
    }
}