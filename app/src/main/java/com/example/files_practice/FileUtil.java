package com.example.files_practice;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    public static final String TAG = FileUtil.class.getSimpleName();

    public static File createInternalStorageFile(Context context, String imageDirectory) {
        Log.d(TAG, "internal storage directory : " + context.getFilesDir());

        File imageFileDirectory = new File(context.getFilesDir(), imageDirectory);

        Log.d(TAG, "internal file directory : " + imageFileDirectory);

        String imageFileName = getNewImageFileName();

        Log.d(TAG, "image file name : " + imageFileName);

        return new File(imageFileDirectory, imageFileName);
    }

    public static File createExternalStorageFile(Context context, String imageDirectory) {
        Log.d(TAG, "internal storage directory : " + context.getFilesDir());

        File imageFileDirectory = context.getExternalFilesDir(imageDirectory);

        Log.d(TAG, "external file directory : " + imageFileDirectory);

        String imageFileName = getNewImageFileName();

        Log.d(TAG, "image file name : " + imageFileName);

        return new File(imageFileDirectory, imageFileName);
    }

    public static File createFileFromUri(Context context, String imageDirectory, Uri uri) {
        Log.d(TAG, "uri : " + uri);

        Log.d(TAG, "external storage directory : " + context.getFilesDir());

        Log.d(TAG, "uri path : " + uri.getPath());

        return new File(uri.getPath());
    }

    private static String getNewImageFileName() {
        return new SimpleDateFormat("yyyy_MM_dd_HHmm", Locale.getDefault()).format(new Date()) + ".jpg";
    }

}
