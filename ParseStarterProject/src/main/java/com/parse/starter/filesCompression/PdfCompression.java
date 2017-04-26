package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Compress the file to pdf format and vice versa for uploading, downloading and viewing from the db
 */

public class PdfCompression extends Compression {

    private Context context;
    private Uri selectedPDFFile;
    private String fileName;
    private String department;
    private  String category;
    private String semester;

    public PdfCompression(Context context, Uri selectedPDFFile, String fileName, String department,
                          String category, String semester) {
        this.context = context;
        this.selectedPDFFile = selectedPDFFile;
        this.fileName = fileName;
        this.department = department;
        this.category = category;
        this.semester = semester;
    }

    public PdfCompression() {

    }

    @Override
    public void upload() {

        try {

            // convert the uri to file using an external third party jar file named commons-io-2.5.jar
            // check the gradle file to check for the dependency of commons-io-2.5.jar

            // fetches the file path
            String filePath = FileChooser.getPath(context, selectedPDFFile);

            // assertion that file path won't be null
            assert filePath != null;

            // creates a file object from the file located at filePath
            File pdfFile = new File(filePath);

            // stores the file directly to byte array
            byte[] fileByteArray = FileUtils.readFileToByteArray(pdfFile);

            // creates a parse file object from the array and names it
            ParseFile parseFile = new ParseFile(fileName, fileByteArray);
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
                    Log.i("Percentage", String.valueOf(percentDone));
                }
            });

            // write the database query to upload the file to database table
            ParseObject file = new ParseObject("file");
            file.put("File_Name", parseFile);
            file.put("Category", category);
            file.put("Dept_Name", department);
            file.put("Semester", semester);

            file.saveInBackground(new SaveCallback() {
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

    @Override
    public void download(String fileName) {

        // write the database query to download the file to from the database
        ParseQuery<ParseObject> fileDownloadQuery = new ParseQuery<>("file");
        fileDownloadQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(object != null) {
                    ParseFile file = (ParseFile) object.get("File_Name");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null && data.length > 0) {
                                Log.i("Info", "we got the data");
                                saveFile(data);
                            }
                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer percentDone) {
                        }
                    });
                }
            }
        });


    }

    private void saveFile(byte[] data) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/download");
        myDir.mkdir();
        File file = new File(myDir, fileName);
        if(file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            FileUtils.writeByteArrayToFile(file,data);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
