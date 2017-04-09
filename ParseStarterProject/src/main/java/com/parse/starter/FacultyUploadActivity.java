package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FacultyUploadActivity extends AppCompatActivity {

    Intent memberLoginIntent;

    Spinner spinner_subject, spinner_category, spinner_semester;

    List<String> categories, semesters, subjects;

    String selectedCategory, selectedSemester, selectedSubject;

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

        fillSpinners(spinner_category, categories);
        onItemClick(spinner_category);


        // create an object the list and enter the semesters
        semesters = new ArrayList<>(Arrays.asList("Semester-1", "Semester-2", "Semester-3",
                "Semester-4", "Semester-5", "Semester-6", "Semester-7", "Semester-8"));

        fillSpinners(spinner_semester, semesters);
        onItemClick(spinner_semester);


        // store the subject in the subject list
        subjects = new ArrayList<>();
        fetchSubjects();

        // uncomment the codes below to fill the spinner_subject with the data fetched
        // and make it function properly
        fillSpinners(spinner_subject, subjects);
        onItemClick(spinner_subject);


    }

    // This method is called to fill up the spinners with the items
    private void fillSpinners(Spinner generalSpinner, List<String> generalList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                generalList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generalSpinner.setAdapter(adapter);
    }

    // this method is called whenever the items in the spinner is clicked
    private void onItemClick(final Spinner generalSpinner) {

        generalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedString = parent.getItemAtPosition(position).toString();
                Toast.makeText(FacultyUploadActivity.this, selectedString, Toast.LENGTH_SHORT).show();
                FacultyUploadActivity.this.assignValues(generalSpinner, selectedString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FacultyUploadActivity.this, "Nothing was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // this method is used to assign the selected values of the spinner into their respected string variables
    public void assignValues(Spinner generalSpinner, String generalString) {
        String generalSpinnerTag = generalSpinner.getTag().toString();
        if(generalSpinnerTag.equalsIgnoreCase("Category")) {
            selectedCategory = generalString;
            Log.i("Info", selectedCategory);
        } else if(generalSpinnerTag.equalsIgnoreCase("Subject")) {
            selectedSubject = generalString;
            Log.i("Info", selectedSubject);
        } else {
            selectedSemester = generalString;
            Log.i("Info", selectedSemester);
        }
    }

    // this method is used to fetch the subject names from the db and store them in subjects list
    private void fetchSubjects() {
        // query the db and store the items in the list subjects
    }

}



