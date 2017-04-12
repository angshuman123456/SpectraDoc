package com.parse.starter.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.parse.starter.filesCompression.ImageCompression;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FacultyUploadActivity extends AppCompatActivity {

    Intent memberLoginIntent;

    Spinner spinner_subject, spinner_category, spinner_semester;

    List<String> categories, semesters, subjects;

    String selectedCategory, selectedSemester, selectedSubject;

    TextView fileName;

    String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_upload);

        memberLoginIntent = getIntent();
        department = memberLoginIntent.getStringExtra("department");

        // inflate the spinners
        spinner_semester = (Spinner) findViewById(R.id.spinner_semester);
        spinner_subject=(Spinner)findViewById(R.id.spinner_subject);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);


        // inflate the fileName text view
        fileName = (TextView) findViewById(R.id.file_name);

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
                if(generalSpinner.getId() == spinner_semester.getId()) {
                    fetchSubjects();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FacultyUploadActivity.this, "Nothing was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // this method is used to assign the selected values of the spinner into their respected string variables
    public void assignValues(Spinner generalSpinner, String generalString) {

        Log.i("Info", "inside assignValues");

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

        Log.i("Info", "inside fetchSubjects");
        Log.i("Info", selectedSemester);
        Log.i("Info", department);

        ParseQuery<ParseObject> subjectQuery = ParseQuery.getQuery("subject");
        subjectQuery.whereEqualTo("Dept_Name", department);
        subjectQuery.whereEqualTo("Semester", selectedSemester);

        subjectQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("Info", "inside parse query");

                Log.i("Info", String.valueOf(objects.size()));

                if(e == null && objects.size() > 0) {
                    Log.i("Info", "inside if");

                    for(ParseObject obj: objects) {
                        subjects.add(obj.getString("Subject_Name"));
                        Log.i("Info", obj.getString("Subject_Name"));
                    }
                    fillSpinners(spinner_subject, subjects);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getFile();
            }
        }
    }


    public void selectFile(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                getFile();
            }
        } else {
            getFile();
        }
    }

    public void getFile() {
        Intent fileFetchIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileFetchIntent.setType("file/*");
        startActivityForResult(fileFetchIntent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == RESULT_OK && data != null) {
            final Uri file = data.getData();
            File file1 = new File(file.toString());
            fileName.setText(file1.getName());


            // need to fetch the name of the file and it's type and compress it and upload it to the db

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ImageCompression imageCompression = new ImageCompression();
                    imageCompression.upload(FacultyUploadActivity.this, file);
                }
            };
            new Thread(runnable).start();
        }
    }

}



