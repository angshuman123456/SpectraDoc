package com.parse.starter.filesCompression;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

/**
 * Compresses the file to image format and vice versa for uploading, viewing and downloading from the db
 */

public class ImageCompression extends Compression {

    private Context context;
    private Uri selectedImage;
    private String fileName;
    private String category;
    private String department;
    private String semester;

    public ImageCompression(Context context, Uri selectedImage, String fileName, String category,
                            String department, String semester) {
        this.context = context;
        this.selectedImage = selectedImage;
        this.fileName = fileName;
        this.department = department;
        this.category = category;
        this.semester = semester;
    }

    public void upload() {

        try {

            // converts the image to bitmap
            Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);

            // creates a byte array output stream object
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // compresses the image to a png format and stores it into the stream object
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, stream);

            // creates a byte array and stores the compressed format stream into byte array
            byte[] byteArray = stream.toByteArray();

            // creates a parse file object from the byte array and names it
            ParseFile parseFile = new ParseFile(fileName, byteArray);
            parseFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Info", "Successful");
                    } else {
                        e.printStackTrace();
                    }
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer percentDone) {
                    Log.i("Info", String.valueOf(percentDone));
                }
            });

            // write the database query to upload the file to database table file
            ParseObject images = new ParseObject("file");
            images.put("File_Name", parseFile);
            images.put("Category", category);
            images.put("Dept_Name", department);
            images.put("Semester", semester);

            images.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        Log.i("Info", "Successful");
                    } else {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(String fileName) {

        // write the database query to download the file to from the database
        ParseQuery<ParseObject> fileDownloadQuery = new ParseQuery<>("file");

    }

    public ParseFile fetchAndView() {

        // write the database query to fetch the file from the database and return it to the calling class

        return null;
    }
}
