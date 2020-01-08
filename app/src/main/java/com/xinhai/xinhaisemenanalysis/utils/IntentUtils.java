package com.xinhai.xinhaisemenanalysis.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 分享类
 */
public class IntentUtils {
    public static final String TYPE_VIDEO = "video/*";
    public static final String TYPE_IMAGE = "image/*";

    public static void shareImage(Context context, File file, String title,String authority) {
        shareFile(context, file, TYPE_IMAGE, title,authority);
    }

    public static void shareVideo(Context context, File file, String title,String authority) {
        shareFile(context, file, TYPE_VIDEO, title,authority);
    }

    public static void shareFile(Context context, File file, String shareType, String title,String authority) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (TYPE_VIDEO.equals(shareType)) {
                fileUri = getVideoContentUri(context, file,authority);
            } else {
                fileUri = FileProvider.getUriForFile(context, authority, file);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setType(shareType);
        Intent chooser = Intent.createChooser(intent, title);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
    }

    private static Uri getVideoContentUri(Context context, File file,String authority) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID}, MediaStore.Video.Media.DATA + "=?",
                new String[]{filePath}, null);
        Uri uri = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Uri baseUri = Uri.parse("content://media/external/video/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }
            cursor.close();
        }

        //如果使用fileProvider获取失败，则使用此方法
        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }

        if (uri == null) {
            uri = FileProvider.getUriForFile(context, authority ,file);
        }
        return uri;
    }

}

