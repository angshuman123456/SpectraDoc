package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

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

    @Override
    public void upload() {

        try {

            // convert the uri to file using an external third party jar file named commons-io-2.5.jar
            // check the gradle file to check for the dependency of commons-io-2.5.jar
//            File pdfFile = new File(selectedPDFFile.getPath(), fileName);


            File pdfFile = new File(FileChooser.getPath(context, selectedPDFFile));
            // stores the file directly to byte array
//            byte[] fileByteArray = FileUtils.readFileToByteArray(pdfFile);

            /**
            * The code below performs the same functionality as that of the above code but without using third party jar
             * not sure if any of the code is correct
             * as it cannot be tested without getting the file from the sd card or internal storage and converting it into a file object
             **/

            // create a file input stream object to read the data from the input stream
            FileInputStream fileInputStream = new FileInputStream(pdfFile);

            // create a byte array output stream object
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // reads the content form the file input stream object and store it in the output stream object
            byte[] buf = new byte[1024];
            try {
                for(int readNum; (readNum = fileInputStream.read(buf)) != -1; ) {
                    stream.write(buf, 0 , readNum);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // convert the output stream object to byte array
            byte[] fileByteArray = stream.toByteArray();
//            Log.i("Info", String.valueOf(fileByteArray.length));

            // creates a parse file object from the array and names it
            ParseFile parseFile = new ParseFile(fileName, fileByteArray);

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
    public void download() {

    }

    @Override
    public ParseFile fetchAndView() {

        return null;
    }
}
