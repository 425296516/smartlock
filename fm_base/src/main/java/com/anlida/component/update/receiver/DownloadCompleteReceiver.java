package com.anlida.component.update.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.anlida.component.update.DownloadApk;
import com.anlida.component.update.loader.FMDownloadManager;
import com.anlida.component.utils.FileUtil;


/**
 * 使用downloadManager下载完毕时会接收到广播
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            long downloadId=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1L);
            install(context,downloadId);
        }
    }

    private void install(Context context,long downloadId){
        SharedPreferences preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        long id=preferences.getLong(DownloadManager.EXTRA_DOWNLOAD_ID,-1L);
        if(id==downloadId){
            FMDownloadManager fmDownloadManager=FMDownloadManager.getInstance(context);
            String path=fmDownloadManager.getDownloadPath(downloadId);
            if(!FileUtil.isApk(path)){
                return;
            }
            if(!TextUtils.isEmpty(path)){
                DownloadApk.startInstall(context,Uri.parse(path));
            }else{
                Toast.makeText(context,"下载失败",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
