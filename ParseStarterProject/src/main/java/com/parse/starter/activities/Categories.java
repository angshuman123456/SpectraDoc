package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    Intent subjectActivityIntent;
    String category, subjectName;
    private ArrayList<String> fileNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        subjectActivityIntent = getIntent();
        subjectName = subjectActivityIntent.getStringExtra("subjectName");
    }

    public void moveToCategoryContentActivity(View view) {

        category = view.getTag().toString();
        Intent categoryContentActivityIntent = new Intent(this, CategoryContent.class);
        categoryContentActivityIntent.putStringArrayListExtra("fileNamesList", fileNamesList);
        startActivity(categoryContentActivityIntent);
    }

    private void setFileNamesList() {

        // query the database and fill the fileNames onto fileNamesList

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

