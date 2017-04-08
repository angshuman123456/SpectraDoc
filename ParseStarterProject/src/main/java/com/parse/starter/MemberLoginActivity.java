package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MemberLoginActivity extends AppCompatActivity {

    Intent mainActivityIntent;
    Switch studentFacultySwitch;
    EditText loginUserId, loginPassword;
    String userName = "", departmentName = "";

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
                    loginUserId.setHint("Department Name");
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

            // write the code for faculty login and move to faculty uploading activity
            ParseQuery<ParseUser> faculty_query = ParseUser.getQuery();
            faculty_query.whereEqualTo("username", loginUserId.getText().toString());
            faculty_query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null && objects.size() > 0) {
                        for(ParseUser obj: objects) {
                            departmentName = obj.getString("Dept_Name");
                        }
                    }
                }
            });
            logInUser();

        } else {

            // write the code for student login move to department activity

            ParseQuery<ParseUser> student_query = ParseUser.getQuery();
            student_query.whereEqualTo("RollNo_Id", Integer.parseInt(loginUserId.getText().toString()));
            student_query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            for (ParseUser obj : objects) {
                                userName = obj.getString("username");
                            }
                        }
                }
            });

            // calling the login function
            logInUser();
        }

        // to check if the button is clicked or functioning properly
        // Log.i("Info", "Login clicked");
    }

    private void logInUser() {

        ParseUser.logInInBackground(userName, loginPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null) {

                    if(studentFacultySwitch.isChecked()) {
                        moveToFacultyActivity();
                    } else {
                        moveToDepartmentActivity();
                    }
                }
            }
        });
    }

    public void forgotPassword(View view) {

        // this function is called whenever "Forgot password" text view is called

        Intent forgotPasswordIntent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(forgotPasswordIntent);
    }

    public void registerActivity(View view) {

        // this function is called whenever "Register" text view is called

        Intent registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerActivityIntent);
    }

    private void moveToDepartmentActivity() {
        Intent departmentIntent = new Intent(this, Department.class);
        startActivity(departmentIntent);
    }

    private  void moveToFacultyActivity() {
        Intent facultyIntent = new Intent(this, FacultyUploadActivity.class);
        facultyIntent.putExtra("department", departmentName);
        startActivity(facultyIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       ParseUser.logOut();
    }
}
