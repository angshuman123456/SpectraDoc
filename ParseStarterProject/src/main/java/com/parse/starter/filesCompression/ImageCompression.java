package com.parse.starter.filesCompression;

import android.content.Context;
import android.net.Uri;

/**
 * Compresses the file to image format and vice versa for uploading, viewing and downloading from the db
 */

public class ImageCompression extends Compression {

    private Context context;
    private Uri selectedImage;

    public ImageCompression(Context context, Uri selectedImage) {
        this.context = context;
        this.selectedImage = selectedImage;
    }

    public void upload() {

    }

    public void download() {

    }

    public void fetchAndView() {

    }
}
