package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;

/**
 * Compress the file to pdf format and vice versa for uploading, downloading and viewing from the db
 */

public class PdfCompression extends Compression {

    private Context context;
    private Uri selectedPDFFile;

    public PdfCompression(Context context, Uri selectedPDFFile) {
        this.context = context;
        this.selectedPDFFile = selectedPDFFile;
    }

    @Override
    public void upload() {

    }

    @Override
    public void download() {

    }

    @Override
    public void fetchAndView() {

    }
}
