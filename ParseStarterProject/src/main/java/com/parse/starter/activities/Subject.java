package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;


public class Subject extends AppCompatActivity {

    Intent semesterActivityIntent;

    ListView subjectList;
    String departmentName;
    String semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        semesterActivityIntent = getIntent();

        // inflated the subjectList
        subjectList = (ListView) findViewById(R.id.subjectList);

        departmentName = semesterActivityIntent.getStringExtra("department");
        semester = semesterActivityIntent.getStringExtra("semester");

        // code to display the subjects in the list view

        ArrayList<String> subjectName = semesterActivityIntent.getStringArrayListExtra("subjectArray");

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjectName);

        subjectList.setAdapter(subjectAdapter);

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String subjectName = (String) parent.getItemAtPosition(position);
                moveToCategoryActivity(subjectName);
            }
        });
    }

    private void moveToCategoryActivity(String subjectName) {

        Intent categoryIntent = new Intent(getApplicationContext(), Categories.class);
        categoryIntent.putExtra("subjectName", subjectName);
        categoryIntent.putExtra("department", departmentName);
        categoryIntent.putExtra("semester", semester);
        startActivity(categoryIntent);
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
