package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryContent extends AppCompatActivity {

    Intent categoriesIntent;
    private ArrayList<String> fileNamesList;

    ListView categoryContent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);

        categoriesIntent = getIntent();
        fileNamesList = categoriesIntent.getStringArrayListExtra("fileNamesList");

        categoryContent = (ListView) findViewById(R.id.categoryContent);

        ArrayAdapter<String> categoryContentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, fileNamesList);
        categoryContent.setAdapter(categoryContentAdapter);
    }
}
