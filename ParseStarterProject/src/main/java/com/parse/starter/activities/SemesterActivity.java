package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class SemesterActivity extends AppCompatActivity {

    Intent getDataFromDeptActivityIntent;
    String departmentName;
    final ArrayList<String> nameOfSubjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        getDataFromDeptActivityIntent = getIntent();

       departmentName = getDataFromDeptActivityIntent.getStringExtra("department");
    }

    public void fetchSemesterNumber(View view) {

        String semester = "Semester-" + view.getTag().toString() ;
        Log.i("Info", semester);
        fetchSubjectsName(semester, departmentName);
    }

    public void fetchSubjectsName(final String semester, final String department) {

        if(nameOfSubjects.size() > 0) {
            nameOfSubjects.clear();
        }


        /* code to connect to db and fetch the subjects of the semester and fill the "nameOfSubjects"
        * with the name of the subjects fetched from the db
         */

        ParseQuery<ParseObject> subjectQuery = ParseQuery.getQuery("subject");
        subjectQuery.whereEqualTo("Dept_Name", department);
        subjectQuery.whereEqualTo("Semester", semester);

        subjectQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0) {

                    for(ParseObject obj: objects) {
                        nameOfSubjects.add(obj.getString("Subject_Name"));
                        Log.i("Info", "subject " + obj.getString("Subject_Name"));
                    }
                    moveToSubjectActivity();
                }
            }
        });


    }

    private void moveToSubjectActivity() {
        Intent subjectsActivityIntent = new Intent(this, Subject.class);
        subjectsActivityIntent.putStringArrayListExtra("subjectArray", nameOfSubjects);
        startActivity(subjectsActivityIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.about_us) {
            // move to about us activity
            Intent i = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(i);
        } else if(item.getItemId() == R.id.about_college) {
            // move to about college activity
            Intent i = new Intent(getApplicationContext(), AboutCollege.class);
            startActivity(i);
        } else if(item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            Intent i = new Intent(getApplicationContext(), MemberLoginActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
