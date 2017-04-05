package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SemesterActivity extends AppCompatActivity {

    Intent getDataFromDepartmentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        getDataFromDepartmentIntent = getIntent();
        String departmentName = getDataFromDepartmentIntent.getStringExtra("department");

    }


}
