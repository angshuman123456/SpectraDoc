/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */


package com.parse.starter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;


public class RegisterActivity extends AppCompatActivity {

    EditText password, name, emailId, department, rollNumber, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        emailId = (EditText) findViewById(R.id.emailId);
        department = (EditText) findViewById(R.id.dept);
        rollNumber = (EditText) findViewById(R.id.roll_number);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUp(View view) {

        if(name.getText().toString().isEmpty() ||
                emailId.getText().toString().isEmpty() ||
                department.getText().toString().isEmpty() ||
                rollNumber.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                confirmPassword.getText().toString().isEmpty()) {

            Toast.makeText(this, "Above fields cannot be empty", Toast.LENGTH_SHORT).show();
        }

        /*
        * This function will be called when registration button will be clicked
        * perform the registration process here
        * write only the necessary code for registration to the database here
         */

        // code to check if password and confirm password fields are same or not
        if( password.getText().toString().equals(confirmPassword.getText().toString())) {

            // write the signup code here

        } else {
            Toast.makeText(this, "Password field does not match with confirm password", Toast.LENGTH_SHORT).show();
        }



        /* code below checks if all the fields are properly working

        Log.i("Info", name.getText().toString());
        Log.i("Info", emailId.getText().toString());
        Log.i("Info", department.getText().toString());
        Log.i("Info", rollNumber.getText().toString());
        Log.i("Info", password.getText().toString());
        Log.i("Info", confirmPassword.getText().toString());

        */
    }

}