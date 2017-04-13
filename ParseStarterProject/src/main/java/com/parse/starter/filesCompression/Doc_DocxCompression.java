package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;

import com.parse.ParseFile;

/**
 * Compress the file to doc format and vice versa for uploading, downloading and viewing from the db
 */

public class Doc_DocxCompression extends Compression {

    private Context context;
    private Uri selectedDocFile;
    private String fileName;

    public Doc_DocxCompression(Context context, Uri selectedDocFile, String fileName) {
        this.context = context;
        this.selectedDocFile = selectedDocFile;
        this.fileName = fileName;
    }

    public void upload() {

    }

    public void download() {

    }

    public ParseFile fetchAndView() {

        return null;
    }
}
