package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.starter.R;

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
        if(view.getTag().toString().equals("it_textView") || view.getTag().equals("it_imageView")) {
            department = "IT";
        } else if(view.getTag().toString().equals("cse_textView") || view.getTag().equals("cse_imageView")) {
            department = "CSE";
        } else if(view.getTag().toString().equals("ee_textView") || view.getTag().equals("ee_imageView")) {
            department = "EE";
        } else if(view.getTag().toString().equals("ece_textView") || view.getTag().equals("ece_imageView")) {
            department = "ECE";
        } if(view.getTag().toString().equals("civil_textView") || view.getTag().equals("civil_imageView")) {
            department = "CIVIL";
        }

        Intent semesterIntent = new Intent(this, SemesterActivity.class);
        semesterIntent.putExtra("department", department);
        startActivity(semesterIntent);
    }
}
