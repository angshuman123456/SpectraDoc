package com.parse.starter.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.filesCompression.FileChooser;
import com.parse.starter.filesCompression.ImageCompression;
import com.parse.starter.filesCompression.PdfCompression;

import org.apache.commons.io.FilenameUtils;

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

    String fetchedFileName = "";

    Uri file;

    ImageCompression imageCompression;
    PdfCompression pdfCompression;

    public static final int GET_FILE_REQUEST_CODE = 2;
    public static final int GET_PERMISSION_REQUEST_CODE = 1;

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

        ParseQuery<ParseObject> subjectQuery = ParseQuery.getQuery("subject");
        subjectQuery.whereEqualTo("Dept_Name", department);
        subjectQuery.whereEqualTo("Semester", selectedSemester);

        subjectQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null && objects.size() > 0) {

                    for(ParseObject obj: objects) {
                        subjects.add(obj.getString("Subject_Name"));
                    }
                    fillSpinners(spinner_subject, subjects);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if(requestCode == GET_PERMISSION_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getFile();
            }
        }
    }


    public void selectFile(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, GET_PERMISSION_REQUEST_CODE);
            } else {
                getFile();
            }
        } else {
            getFile();
        }
    }

    public void getFile() {
        Intent fileFetchIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileFetchIntent.setType("*/*");
        startActivityForResult(fileFetchIntent, GET_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // code to fetch the file, extract the file name and it's type and create object respectively and upload
            file = data.getData();
            fetchedFileName = FilenameUtils.getName(FileChooser.getPath(getApplicationContext(), file));
            fileName.setText(fetchedFileName);
        }
    }

    public void uploadFile(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if(checkFileTypeImage()) {
                imageCompression = new ImageCompression(FacultyUploadActivity.this, file, fetchedFileName, selectedCategory,
                        department, selectedSemester, selectedSubject);

                imageCompression.upload();
                } else {

                    pdfCompression = new PdfCompression(FacultyUploadActivity.this,
                            file, fetchedFileName, selectedCategory, department, selectedSemester);
                    pdfCompression.upload();
                }

            }
        };
        new Thread(runnable).start();
    }

    private boolean checkFileTypeImage() {
        final String[] okFileExtensions = new String[] {"jpg", "png", "gif", "jpeg"};
        String fileType = FilenameUtils.getExtension(fetchedFileName);
        for(String extension: okFileExtensions) {
            if (fileType.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
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



