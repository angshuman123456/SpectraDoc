package com.parse.starter.filesCompression;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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
    private String subject;



    public ImageCompression(Context context, Uri selectedImage, String fileName, String category,
                            String department, String semester, String subject) {
        this.context = context;
        this.selectedImage = selectedImage;
        this.fileName = fileName;
        this.department = department;
        this.category = category;
        this.semester = semester;
        this.subject = subject;

    }

    public ImageCompression() {
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

                }
            });

            // write the database query to upload the file to database table file
            ParseObject images = new ParseObject("file");
            images.put("File_Name", parseFile);
            images.put("Category", category);
            images.put("Dept_Name", department);
            images.put("Semester", semester);
            images.put("subject_Name", subject);
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



    public void download(final String fileNameDownload) {


        // write the database query to download the file to from the database
        ParseQuery<ParseObject> fileDownloadQuery = new ParseQuery<>("file");
        fileDownloadQuery.whereEqualTo("File_Name", fileNameDownload);
        fileDownloadQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0) {
                    for(ParseObject object: objects) {
                        ParseFile file =  object.getParseFile("File_Name");
                        Log.i("Info", file.getName());
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null && data.length > 0) {
                                    Log.i("Info", "we got the data ");
                                    Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    saveImage(img, fileNameDownload);
                                }
                            }
                        }, new ProgressCallback() {
                            @Override
                            public void done(Integer percentDone) {
                                Log.i("Info", String.valueOf(percentDone));
                            }
                        });
                    }
                }
            }

        });

    }

    private void saveImage(Bitmap finalBitmap, String fileNameDownload) {
        String root = Environment.getExternalStorageDirectory().toString();
        Log.i("Root path", root);
        File myDir = new File(root + "/saved_image");
        myDir.mkdir();
        File file = new File(myDir, fileNameDownload);
        if(file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
