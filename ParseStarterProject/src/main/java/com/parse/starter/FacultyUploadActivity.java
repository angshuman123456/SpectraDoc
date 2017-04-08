package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FacultyUploadActivity extends AppCompatActivity {

    Intent memberLoginIntent;

    Spinner spinner_subject, spinner_category, spinner_semester;

    List<String> categories;

    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_upload);

        memberLoginIntent = getIntent();
        memberLoginIntent.getStringExtra("department");

        // inflate the spinners
        spinner_semester = (Spinner) findViewById(R.id.spinner_semester);
        spinner_subject=(Spinner)findViewById(R.id.spinner_subject);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);


        // create an object of the list and enter the categories
        categories = new ArrayList<>(Arrays.asList("Assignment", "E-book",
                "Notes", "Notices", "Syllabus"));

        // filling up the spinner with the help of an array adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, categories);
        spinner_category.setAdapter(arrayAdapter);

        // query the dp and store the semester in the spinner_semester


        // query the dp and store the subject in the spinner_subject



        // static entries. Since there are only few known categories in the spinner_category

    }


    }



