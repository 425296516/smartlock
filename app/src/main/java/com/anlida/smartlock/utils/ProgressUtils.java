package com.anlida.smartlock.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by (张慈瑞).
 * dialog
 */
public class ProgressUtils {

    private static ProgressDialog dialog;

    public static void show(Activity mContext, String message) {
        dialog = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setMessage(message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog!=null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
