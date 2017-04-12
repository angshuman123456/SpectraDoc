package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.starter.R;

import java.util.ArrayList;


public class Subject extends AppCompatActivity {

    Intent semesterActivityIntent;

    ListView subjectList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        semesterActivityIntent = getIntent();

        // inflated the subjectList
        subjectList = (ListView) findViewById(R.id.subjectList);

        // write the code to fetch the subject and store the length of the data fetched from the db and store it in the length variable


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

        Intent categoryIntent = new Intent();
        categoryIntent.putExtra("subjectName", subjectName);
        startActivity(categoryIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
