package com.parse.starter.filesCompression;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Compresses the file to image format and vice versa for uploading, viewing and downloading from the db
 */

public class ImageCompression {

    public void upload(Context context, Uri selectedImage) {
        try {
            Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
            Log.i("info", "image received");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
