package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        Intent categoryContentActivityIntent = new Intent(this, FilesViewer.class);
        categoryContentActivityIntent.putStringArrayListExtra("fileNamesList", fileNamesList);
        startActivity(categoryContentActivityIntent);
    }
}

