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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.filesCompression.ImageCompression;
import com.parse.starter.filesCompression.PdfCompression;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

public class CategoryContent extends AppCompatActivity {

    Intent categoriesIntent;
    private ArrayList<String> fileNamesList;

    ListView categoryContent;
    SearchView searchFile;
    ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);
        imageView = (ImageView) findViewById(R.id.img);

        categoriesIntent = getIntent();


        fileNamesList = categoriesIntent.getStringArrayListExtra("fileNamesList");

        for(String name: fileNamesList) {
            Log.i("Subject name", name);
        }


        categoryContent = (ListView) findViewById(R.id.categoryContent);
        searchFile = (SearchView) findViewById(R.id.searchFile);

        final ArrayAdapter<String> categoryContentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, fileNamesList);
        categoryContent.setAdapter(categoryContentAdapter);

        categoryContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = categoryContentAdapter.getItem(position);
                if(checkFileTypeImage(name)) {
                    ImageCompression img = new ImageCompression();
                    img.download(name);

                } else {
                    PdfCompression pdf = new PdfCompression();
                    pdf.download(name);
                }

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

    private boolean checkFileTypeImage(String fetchedFileName) {
        final String[] okFileExtensions = new String[] {"jpg", "png", "gif", "jpeg"};
        String fileType = FilenameUtils.getExtension(fetchedFileName);
        for(String extension: okFileExtensions) {
            if (fileType.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
