package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        fileNamesList = new ArrayList<>();

    }

    public void filesView(View view) {
        category = view.getTag().toString();

        // query the db and store the files in a list and call the method moveToCategoryContentActivity
    }

    private void moveToCategoryContentActivity() {

        Intent categoryContentActivityIntent = new Intent(this, FilesViewer.class);
        categoryContentActivityIntent.putStringArrayListExtra("fileNamesList", fileNamesList);
        startActivity(categoryContentActivityIntent);
    }
}

