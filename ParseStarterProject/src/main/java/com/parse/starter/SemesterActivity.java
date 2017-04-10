package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

        String semester = "semester-" + view.getTag().toString() ;
        Log.i("Info", semester);
        fetchSubjectsName(semester, departmentName);
    }

    private void fetchSubjectsName(final String semester, final String department) {


        /* code to connect to db and fetch the subjects of the semester and fill the "nameOfSubjects"
        * with the name of the subjects fetched from the db
         */

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("subject");
        query1.whereEqualTo("Dept_Name", department);
        query1.whereEqualTo("Semester", semester);

        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null && objects.size() > 0) {

                    for(ParseObject obj: objects) {
                        nameOfSubjects.add(obj.getString("Subject_Name"));
                    }
                }
            }
        });

        moveToSubjectActivity();
    }

    private void moveToSubjectActivity() {
        Intent subjectsActivityIntent = new Intent(this, Subject.class);
        subjectsActivityIntent.putStringArrayListExtra("subjectArray", nameOfSubjects);
        startActivity(subjectsActivityIntent);
    }
}
