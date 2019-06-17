package com.anlida.component.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.MainThread;

import com.anlida.component.update.loader.FMDownloadManager;
import com.anlida.component.utils.MD5Utils;

public class FileLoader {

    @MainThread
    public static void download(Context context, String url, String title, String appName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        long downloadId = sharedPreferences.getLong(MD5Utils.MD5(title), -1L);
        if (downloadId != -1L) {
            FMDownloadManager downloadManager = FMDownloadManager.getInstance(context);
            int status = downloadManager.getDownloadStatus(downloadId);
            if (status == DownloadManager.STATUS_FAILED) {
                startDownload(context, url, title, appName);
            }
        } else {
            startDownload(context, url, title, appName);
        }
    }

    public static void remove(Context context, String keyName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        long downloadId = sharedPreferences.getLong(MD5Utils.MD5(keyName), -1L);
        if (downloadId != -1) {
            FMDownloadManager downloadManager = FMDownloadManager.getInstance(context);
            sharedPreferences.edit().remove(MD5Utils.MD5(keyName)).apply();
            downloadManager.getDownloadManager().remove(downloadId);

        }

    }

    public static int getLoadStatus(Context context, String keyName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        long downloadId = sharedPreferences.getLong(MD5Utils.MD5(keyName), -1L);
        if (downloadId != -1L) {
            FMDownloadManager downloadManager = FMDownloadManager.getInstance(context);
            return downloadManager.getDownloadStatus(downloadId);
        }
        return -1;
    }

    public static int getDownloadPercent(Context context, String keyName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        long downloadId = sharedPreferences.getLong(MD5Utils.MD5(keyName), -1L);
        if (downloadId != -1L) {
            FMDownloadManager downloadManager = FMDownloadManager.getInstance(context);
            return downloadManager.getDownloadPercent(downloadId);
        }
        return 0;
    }


    /**
     * 开始下载
     */
    private static void startDownload(Context context, String url, String title, String appName) {
        FMDownloadManager fmDownloadManager = FMDownloadManager.getInstance(context);
        long downloadId = fmDownloadManager.downloadFile(url, title, "下载完成后打开", appName);
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(MD5Utils.MD5(title), downloadId).apply();
    }


}
