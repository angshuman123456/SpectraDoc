package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.List;

/**
 * This activity is used to reset the password
 */

public class PasswordChangeActivity extends AppCompatActivity {

    EditText newPassword, confirmPassword;
    Intent i;
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        i = getIntent();
        emailId = i.getStringExtra("emailId");

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

                            objects.get(0).setPassword(confirmPassword.getText().toString());
                    }
                }
            });

        } else {
            Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
        }
    }
}
