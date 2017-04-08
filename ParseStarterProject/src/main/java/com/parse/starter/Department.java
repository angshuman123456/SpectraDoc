package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Department extends AppCompatActivity {

    TextView it_textView, ece_textView, civil_textView, ee_textView, cse_textView;
    ImageView it_imageView, ece_imageView, civil_imageView, ee_imageView, cse_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        // inflate the text views

        it_textView = (TextView) findViewById(R.id.it_textView);
        ece_textView = (TextView) findViewById(R.id.ece_textView);
        civil_textView = (TextView) findViewById(R.id.civil_textView);
        ee_textView = (TextView) findViewById(R.id.ee_textView);
        cse_textView = (TextView) findViewById(R.id.cse_textView);

        // inflate the image view
        it_imageView = (ImageView) findViewById(R.id.it_imageView);
        ece_imageView = (ImageView) findViewById(R.id.ece_imageView);
        civil_imageView = (ImageView) findViewById(R.id.civil_imageView);
        ee_imageView = (ImageView) findViewById(R.id.ee_imageView);
        cse_imageView = (ImageView) findViewById(R.id.cse_imageView);
    }


    // will jump to semester activity and also send which dept is selected
    public void semesterSelect(View view) {

        String department = "";

        // write the code to fetch the dept name from the database

        Intent semesterIntent = new Intent(this, SemesterActivity.class);
        semesterIntent.putExtra("department", "put the dept name here after fetching from the db");
        startActivity(semesterIntent);
    }
}
