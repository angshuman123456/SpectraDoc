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

public class Categories extends AppCompatActivity {

    Intent subjectActivityIntent;
    String category, subjectName, department, semester;
    private ArrayList<String> fileNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        subjectActivityIntent = getIntent();
        subjectName = subjectActivityIntent.getStringExtra("subjectName");
        department = subjectActivityIntent.getStringExtra("department");
        semester = subjectActivityIntent.getStringExtra("semester");

        fileNamesList = new ArrayList<>();
    }

    public void moveToCategoryContentActivity(View view) {
        category = view.getTag().toString();

        setFileNamesList();
    }

    private void setFileNamesList() {

//        Log.i("Info", "inside set file name list");
//        Log.i("Info", category);
//        Log.i("Info", subjectName);
//        Log.i("Info", department);
//        Log.i("Info", semester);


        // query the database and fill the fileNames onto fileNamesList
        ParseQuery<ParseObject> fileNames = new ParseQuery<>("file");
        fileNames.whereEqualTo("Category", category);
        fileNames.whereEqualTo("subject_Name", subjectName);
        fileNames.whereEqualTo("Dept_Name", department);
        fileNames.whereEqualTo("Semester", semester);

        fileNames.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects.size() > 0) {

                    Log.i("Info", "inside fileNamesQuery");

                    for (ParseObject object : objects) {
                        fileNamesList.add(object.getParseFile("File_Name").getName());
                    }


                    Intent categoryContentActivityIntent = new Intent(Categories.this, CategoryContent.class);

                    Log.i("FileNamesList Size", String.valueOf(fileNamesList.size()));
                    categoryContentActivityIntent.putStringArrayListExtra("fileNamesList", fileNamesList);
                    startActivity(categoryContentActivityIntent);
                }
            }
        });

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

