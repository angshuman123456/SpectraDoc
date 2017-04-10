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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SemesterActivity extends AppCompatActivity {

    Intent getDataFromDeptActivityIntent;
    String departmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        getDataFromDeptActivityIntent = getIntent();

       departmentName = getDataFromDeptActivityIntent.getStringExtra("department");
    }

    public void fetchSemesterNumber(View view) {

        String tagId = (String) view.getTag();

        if(tagId.equals("1")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("2")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("3")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("4")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("5")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("6")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("7")) {
            tagId = "semester-" + tagId;
        }

        if(tagId.equals("8")) {
            tagId = "semester-" + tagId;
        }

        fetchSubjectsName(tagId, departmentName);
    }

    private void fetchSubjectsName(final String semester, final String department) {

        final ArrayList<String> nameOfSubjects = new ArrayList<>();

        /* write the code to connect to db and fetch the subjects of the semester and fill the "nameOfSubjects"
        * with the name of the subjects fetched from the db
         */

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("subject");
        query1.whereEqualTo("Dept_Name", department);
        query1.whereEqualTo("Semester", semester);

        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                Log.i("Info", department);
                if(e == null && objects.size() > 0) {


                    Log.i("Info", "class sort listed according to department name ");

                    for(ParseObject obj: objects) {
                        Log.i("Info", obj.getString("Subject_Name"));
                    }
                }
            }
        });




        Intent subjectsActivityIntent = new Intent(this, Subject.class);
        subjectsActivityIntent.putStringArrayListExtra("subjectArray", nameOfSubjects);
        startActivity(subjectsActivityIntent);
    }
}
