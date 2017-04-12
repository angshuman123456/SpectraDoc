package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;

/**
 * Compress the file to doc format and vice versa for uploading, downloading and viewing from the db
 */

public class Doc_DocxCompression extends Compression {

    private Context context;
    private Uri selectedDocFile;

    public Doc_DocxCompression(Context context, Uri selectedDocFile) {
        this.context = context;
        this.selectedDocFile = selectedDocFile;
    }

    public void upload() {

    }

    public void download() {

    }

    public void fetchAndView() {

    }
}
