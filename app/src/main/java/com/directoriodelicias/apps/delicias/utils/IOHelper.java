package com.directoriodelicias.apps.delicias.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author mcxiaoke
 * @version 1.2 2012.02.22
 */
public final class IOHelper {
    public static final SimpleDateFormat FILENAME_FORMAT = new SimpleDateFormat(
            "'fanfou'_yyyyMMdd_HHmmss.'jpg'");

    private IOHelper() {
        throw new IllegalAccessError("此类为静态工具类，不能被实例化");
    }

    public static File getImageCacheDir(Context context) {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(),
                    "/Android/data/" + context.getPackageName() + "/photocache");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
            File nomedia = new File(cacheDir, ".nomedia");
            if (!nomedia.exists()) {
                nomedia.mkdirs();
            }
        }
        return cacheDir;
    }

    public static File getPhotoDir(Context context) {
        File photoDir;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            photoDir = new File(Environment.getExternalStorageDirectory(),
                    "/DCIM/FANFOU");
        } else {
            photoDir = context.getCacheDir();
        }
        if (!photoDir.exists()) {
            photoDir.mkdirs();
        }
        return photoDir;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        // get path from uri like content://media//
        Cursor cursor = null;
        String path = null;
        try {
            cursor = context.getContentResolver().query(contentUri,
                    new String[]{MediaColumns.DATA}, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (path == null) {
            path = contentUri.getPath();
        }
        return path;
    }

    public static void deleteDir(File target) {
        if (!target.exists()) {
            return;
        }
        if (target.isFile()) {
            target.delete();
        }

        if (target.isDirectory()) {
            File[] files = target.listFiles();
            for (File file : files) {
                deleteDir(file);
            }
            target.delete();
        }
    }

    public static void deleteDir(File target, int minFileSize) {
        if (!target.exists()) {
            return;
        }
        if (target.isFile()) {
            if (target.length() > minFileSize) {
                target.delete();
            }
        }

        if (target.isDirectory()) {
            File[] files = target.listFiles();
            for (File file : files) {
                deleteDir(file, minFileSize);
            }
        }
    }

    public static void forceClose(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
        }
    }

}
