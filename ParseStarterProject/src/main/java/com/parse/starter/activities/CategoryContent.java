package com.parse.starter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryContent extends AppCompatActivity {

    Intent categoriesIntent;
    private ArrayList<String> fileNamesList;

    ListView categoryContent;
    SearchView searchFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);

        categoriesIntent = getIntent();
        // fileNamesList = categoriesIntent.getStringArrayListExtra("fileNamesList");
        fileNamesList = new ArrayList<>(Arrays.asList("file1", "file2", "file3"));

        categoryContent = (ListView) findViewById(R.id.categoryContent);
        searchFile = (SearchView) findViewById(R.id.searchFile);

        final ArrayAdapter<String> categoryContentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, fileNamesList);
        categoryContent.setAdapter(categoryContentAdapter);

        categoryContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = categoryContentAdapter.getItem(position);
                Log.d("Filename", name);
            }
        });

        searchFile.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryContentAdapter.getFilter().filter(newText);
                return false;
            }
        });

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
