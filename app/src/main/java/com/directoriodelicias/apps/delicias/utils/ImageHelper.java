package com.directoriodelicias.apps.delicias.utils;

/**
 * Created by Directorio on 9/29/2016.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppContext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mcxiaoke
 * @version 3.2 2011.12.26
 */
final public class ImageHelper {

    public static final int IMAGE_QUALITY_HIGH = 90;
    public static final int IMAGE_QUALITY_MEDIUM = 80;
    public static final int IMAGE_QUALITY_LOW = 70;
    public static final int IMAGE_MAX_WIDTH = 1080;// 640 596
    //public static final int IMAGE_MAX_HEIGHT = 1200;// 1320 1192
    public static final int OUTPUT_BUFFER_SIZE = 8196;
    private static final String TAG = ImageHelper.class.getSimpleName();

    // public static Bitmap resampleImage(String path, int maxDim)
    // throws Exception {
    //
    // BitmapFactory.Options bfo = new BitmapFactory.Options();
    // bfo.inJustDecodeBounds = true;
    // BitmapFactory.decodeFile(path, bfo);
    //
    // BitmapFactory.Options optsDownSample = new BitmapFactory.Options();
    // optsDownSample.inSampleSize = getClosestResampleSize(bfo.outWidth,
    // bfo.outHeight, maxDim);
    //
    // Bitmap bmpt = BitmapFactory.decodeFile(path, optsDownSample);
    //
    // Matrix m = new Matrix();
    //
    // if (bmpt.getWidth() > maxDim || bmpt.getHeight() > maxDim) {
    // BitmapFactory.Options optsScale = getResampling(bmpt.getWidth(),
    // bmpt.getHeight(), maxDim);
    // m.postScale((float) optsScale.outWidth / (float) bmpt.getWidth(),
    // (float) optsScale.outHeight / (float) bmpt.getHeight());
    // }
    //
    // int sdk = new Integer(Build.VERSION.SDK).intValue();
    // if (sdk > 4) {
    // int rotation = getExifOrientation(path);
    // if (rotation != 0) {
    // m.postRotate(rotation);
    // }
    // }
    // return Bitmap.createBitmap(bmpt, 0, 0, bmpt.getWidth(),
    // bmpt.getHeight(), m, true);
    // }

    public static Bitmap resampleImage(String path, int maxDim)
            throws Exception {

        Options bfo = new Options();
        bfo.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bfo);

        Options optsDownSample = new Options();
        optsDownSample.inSampleSize = getClosestResampleSize(bfo.outWidth,
                bfo.outHeight, maxDim);

        Bitmap bmpt = BitmapFactory.decodeFile(path, optsDownSample);

        Matrix m = new Matrix();

        if (bmpt.getWidth() > maxDim || bmpt.getHeight() > maxDim) {
            Options optsScale = getResampling(bmpt.getWidth(),
                    bmpt.getHeight(), maxDim);
            m.postScale((float) optsScale.outWidth / (float) bmpt.getWidth(),
                    (float) optsScale.outHeight / (float) bmpt.getHeight());
        }

        int sdk = new Integer(Build.VERSION.SDK).intValue();
        if (sdk > 4) {
            int rotation = getExifOrientation(path);
            if (rotation != 0) {
                m.postRotate(rotation);
            }
        }
        return Bitmap.createBitmap(bmpt, 0, 0, bmpt.getWidth(),
                bmpt.getHeight(), m, true);
    }

    private static Options getResampling(int cx, int cy, int max) {
        float scaleVal = 1.0f;
        Options bfo = new Options();
        if (cx > cy) {
            scaleVal = (float) max / (float) cx;
        } else if (cy > cx) {
            scaleVal = (float) max / (float) cy;
        } else {
            scaleVal = (float) max / (float) cx;
        }
        bfo.outWidth = (int) (cx * scaleVal + 0.5f);
        bfo.outHeight = (int) (cy * scaleVal + 0.5f);
        return bfo;
    }

    private static int getClosestResampleSize(int cx, int cy, int maxDim) {
        int max = Math.max(cx, cy);

        int resample = 1;
        for (resample = 1; resample < Integer.MAX_VALUE; resample++) {
            if (resample * maxDim > max) {
                resample--;
                break;
            }
        }

        if (resample > 0) {
            return resample;
        }
        return 1;
    }

    private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName = Environment.getExternalStorageDirectory()
                .toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory.canWrite();
    }

    public static boolean hasStorage(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (requireWriteAccess) {
                boolean writable = checkFsWritable();
                return writable;
            } else {
                return true;
            }
        } else return !requireWriteAccess
                && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif");
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }

    public static boolean writeToFile(File file, Bitmap bitmap) {
        if (bitmap == null || file == null || file.exists()) {
            return false;
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file),
                    OUTPUT_BUFFER_SIZE);
            return bitmap.compress(CompressFormat.JPEG, 100, bos);
        } catch (IOException e) {
            if (AppContext.DEBUG) {
                Log.d(TAG, "writeToFile:" + e.getMessage());
            }
        } finally {
            IOHelper.forceClose(bos);
        }
        return false;
    }

    public static File compressForUpload(String srcFileName,
                                         String destFileName, Bitmap imgBitmap, int maxWidth, int quality) {
        Bitmap bitmap = null;
        if (imgBitmap != null) {
            bitmap = imgBitmap;
        } else {
            bitmap = compressImage(srcFileName, maxWidth);
        }
        if (bitmap == null) {
            return null;
        }
        if (AppContext.DEBUG) {
            Log.d(TAG, "compressForUpload bitmap=(" + bitmap.getWidth() + ","
                    + bitmap.getHeight() + ")");
        }
        FileOutputStream fos = null;
        try {
            CompressFormat format = CompressFormat.JPEG;
            if (srcFileName.toLowerCase().lastIndexOf("png") > -1) {
                format = CompressFormat.PNG;
            }
            if (quality > IMAGE_QUALITY_HIGH) {
                quality = IMAGE_QUALITY_HIGH;
            } else if (quality < IMAGE_QUALITY_LOW) {
                quality = IMAGE_QUALITY_LOW;
            }
            fos = new FileOutputStream(destFileName);
            bitmap.compress(format, quality, fos);
            return new File(destFileName);
        } catch (FileNotFoundException e) {
            if (AppContext.DEBUG) {
                e.printStackTrace();
            }
            return null;
        } finally {
            IOHelper.forceClose(fos);
        }
    }

    public static Bitmap compressImage(String path, int maxDim) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int inSampleSize = 1;
        for (int w = options.outWidth; w > maxDim * 2; w /= 2) {
            inSampleSize += 1;
        }
        if (AppContext.DEBUG) {
            Log.d(TAG, "compressImage original=(" + options.outWidth + ","
                    + options.outHeight + ")");
            Log.d(TAG, "compressImage inSampleSize=" + inSampleSize);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap != null) {
            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();
            Matrix m = new Matrix();
            if (bw > maxDim) {
                float scale = (float) maxDim / (float) bw;
                m.postScale(scale, scale);
                if (AppContext.DEBUG) {
                    Log.d(TAG, "compressImage matrix scale=" + scale);
                }
            }
            int rotation = getExifOrientation(path);
            if (getExifOrientation(path) != 0) {
                m.postRotate(rotation);
            }
            if (AppContext.DEBUG) {
                Log.d(TAG, "compressImage matrix rotation=" + rotation);
                Log.d(TAG, "compressImage bitmap=(" + bw + "," + bh + ")");
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bw, bh, m, true);
        }
        return null;

    }

    /**
     * apply filter to a bitmap
     *
     * @param original
     * @param filter
     * @return filtered bitmap
     */
    // public static Bitmap applyFilter(Bitmap original, FilterBase filter) {
    // return filter.filter(original);
    // }
    private static int computeSampleSize(InputStream stream, int maxW, int maxH) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        double w = options.outWidth;
        double h = options.outHeight;
        int sampleSize = (int) Math.ceil(Math.max(w / maxW, h / maxH));
        return sampleSize;
    }

    private static int computeSampleSize(String path, int maxW, int maxH) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        double w = options.outWidth;
        double h = options.outHeight;
        int sampleSize = (int) Math.ceil(Math.max(w / maxW, h / maxH));
        return sampleSize;
    }

    public static Bitmap scaleImageFromUri(Context context, Uri uri, int size) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            is.close();
            int scale = 1;
            while ((options.outWidth / scale > size)
                    || (options.outHeight / scale > size)) {
                scale *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            is = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return bitmap;
    }

    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

}