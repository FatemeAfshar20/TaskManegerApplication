package com.example.taskmanegerapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhotoUtils {
    private static File mPhotoFile;
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final String AUTHORITY =
            "com.example.taskmanegerapplication.fileprovider";

    public static void takePhoto(Context context){
        Activity activity= (Activity) context;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            mPhotoFile = null;

            try {
                mPhotoFile = createImageFile(activity);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (mPhotoFile != null) {
                Uri photoUri = getPhotoUri(activity);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
            }
        }
    }

    public static File createImageFile(Context context) throws IOException {
        Activity activity= (Activity) context;
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =activity.getFilesDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private static Uri getPhotoUri(Context context) {
        Activity activity= (Activity) context;
        return FileProvider.getUriForFile(activity,
                AUTHORITY, mPhotoFile);
    }

    public static String receiveResult(Context context){
        Activity activity= (Activity) context;
        Uri photoUri = getPhotoUri(activity);

        activity.revokeUriPermission(photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        return mPhotoFile.getAbsolutePath();
    }
}
