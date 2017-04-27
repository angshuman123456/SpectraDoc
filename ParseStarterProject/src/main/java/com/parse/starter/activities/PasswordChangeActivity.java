package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.List;

/**
 * This activity is used to reset the password but currently inactive
 */

public class PasswordChangeActivity extends AppCompatActivity {

    EditText newPassword, confirmPassword;
    Intent i;
    String emailId, sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        i = getIntent();
        emailId = i.getStringExtra("emailId");
        sessionToken = i.getStringExtra("token");

        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
    }

    public void resetPassword(View view) {

        if(newPassword.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, " Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else if(newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            // code to update the password
            ParseQuery<ParseUser> passwordChange = ParseUser.getQuery();
            passwordChange.whereEqualTo("email", emailId).setLimit(1);
            passwordChange.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null && objects.size() > 0) {
                        ParseUser user = objects.get(0);
                        Log.i("username", user.toString());


                        String sessionToken = user.getSessionToken();
                        Log.i("Session Token", sessionToken);
                        ParseUser.becomeInBackground(sessionToken, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null) {
                                    user.setPassword(confirmPassword.getText().toString());
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    Intent i = new Intent(getApplicationContext(), MemberLoginActivity.class);
                    startActivity(i);
                }
            });

        } else {
            Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
        }
    }
}
