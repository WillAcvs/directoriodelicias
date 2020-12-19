package com.directoriodelicias.apps.delicias.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Droideve on 9/26/2016.
 */

public class ImageUtils {


    public static ImageUtils mInstant;

    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (AppConfig.APP_DEBUG)
                e.printStackTrace();
            return null;

        }
    }

    public Bitmap prepareOrientationBitmap(Context context, Uri myUri) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(myUri, null);

            File imageFile = new File(myUri.toString());
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Bitmap rotattedBitmap = BitmapFactory.decodeFile(myUri.toString());
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            return Bitmap.createBitmap(rotattedBitmap, 0, 0, rotattedBitmap.getWidth(), rotattedBitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage, String dest) {

        //yteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
//                inContext.getResources().getString(R.string.app_name), null);
        try {
            FileOutputStream out = new FileOutputStream(dest);
            inImage.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Uri.parse(dest);
    }


    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI, Context context) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    public static class PrepareImagesData extends AsyncTask<Uri, Uri, Uri> {


        private String pathFile;
        private String pathDest;
        private Bitmap imgBitMap;

        private OnCompressListner onCompressed;


        public PrepareImagesData(Context context, String pathFile, String pathDest, OnCompressListner onCompressed) {
            this.pathDest = pathDest;
            this.pathFile = pathFile;
            setListener(onCompressed);


        }

        public PrepareImagesData(Context context, String pathFile, Bitmap imgBitMap, String pathDest, OnCompressListner onCompressed) {
            this.pathDest = pathDest;
            this.imgBitMap = imgBitMap;
            this.pathFile = pathFile;


            setListener(onCompressed);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {


            } catch (Exception e) {

            }

        }

        @Override
        protected Uri doInBackground(Uri... params) {


            if (AppContext.DEBUG) {
                Log.e("pathFile", pathFile);
                Log.e("pathDest", pathDest);
            }


            File file = ImageHelper.compressForUpload(pathFile, pathDest, imgBitMap, ImageHelper.IMAGE_MAX_WIDTH,
                    ImageHelper.IMAGE_QUALITY_HIGH);

            if (Objects.requireNonNull(file).exists()) {

                return Uri.parse(file.getPath());
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Uri uri) {

            if (AppContext.DEBUG && uri != null) {
                Log.e("fileCompressed", uri.getPath());
            }

            if (onCompressed != null) {
                onCompressed.onCompressed(uri.toString(), pathFile);
            }

            super.onPostExecute(uri);
        }

        public void setListener(OnCompressListner listner) {

            this.onCompressed = listner;

        }

        public interface OnCompressListner {
            void onCompressed(String newPath, String oldPath);
        }


    }
}
