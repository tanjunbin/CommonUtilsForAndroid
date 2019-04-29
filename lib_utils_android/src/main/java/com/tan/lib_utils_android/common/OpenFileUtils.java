package com.tan.lib_utils_android.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Locale;

public class OpenFileUtils {

    public static void openFile(String filePath, Context context) {

        File file = new File(filePath);
        if (!file.exists())
            return;
        Intent intent = null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav") || end.equals("amr")) {
            intent = getAudioFileIntent(filePath, context);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            intent = getVideoFileIntent(filePath, context);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            intent = getImageFileIntent(filePath, context);
        } else if (end.equals("apk")) {
            intent = getApkFileIntent(filePath, context);
        } else if (end.equals("ppt") || end.equals("pptx")) {
            intent = getPptFileIntent(filePath, context);
        } else if (end.equals("xls") || end.equals("xlsx")) {
            intent = getExcelFileIntent(filePath, context);
        } else if (end.equals("doc") || end.equals("docx") || end.equals("dot") || end.equals("dotx")) {
            intent = getWordFileIntent(filePath, context);
        } else if (end.equals("pdf")) {
            intent = getPdfFileIntent(filePath, context);
        } else if (end.equals("chm")) {
            intent = getChmFileIntent(filePath, context);
        } else if (end.equals("txt")) {
            intent = getTextFileIntent(filePath, false, context);
        } else {
            intent = getAllIntent(filePath, context);
        }
        if (intent == null) return;
        context.startActivity(intent);
    }

    public static Uri getUriForOpen(final String filePath, final Intent intent, final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (intent != null) intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return FileProvider.getUriForFile(context, "com.fostha.edu_teacher.fileprovider", new File(filePath));

        }

        return Uri.fromFile(new File(filePath));
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param, final Context context) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getUriForOpen(param, intent, context);//Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param, final Context context) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    // Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = getUriForOpen(param, intent, context);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri = getUriForOpen(param, intent, context);
            intent.setDataAndType(uri, "text/plain");
        }
        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param, final Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriForOpen(param, intent, context);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

}