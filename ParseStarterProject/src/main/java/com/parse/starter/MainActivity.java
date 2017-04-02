/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseAnalytics;


public class MainActivity extends AppCompatActivity {

    EditText userId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = (EditText) findViewById(R.id.roll_number);
        password = (EditText) findViewById(R.id.password);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUp(View view) {
        Log.i("Info", "Signup clicked");
    }

    public void loginActivity(View view) {
        Intent memberLoginActivityIntent = new Intent(getApplicationContext(), MemberLoginActivity.class);
        startActivity(memberLoginActivityIntent);
    }
}