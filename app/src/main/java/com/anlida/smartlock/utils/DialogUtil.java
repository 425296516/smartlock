package com.anlida.smartlock.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.anlida.smartlock.R;


/**
 * Created by zhangcirui on 16/3/14.
 */
public class DialogUtil {

    private static final String TAG = DialogUtil.class.getSimpleName();

    public static ProgressDialog progressDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
        View view = dialog.getLayoutInflater().inflate(R.layout.dialog_loading, null);
        dialog.setCancelable(false);

        dialog.show();

        dialog.setContentView(view);

        return dialog;
    }

    public static ProgressDialog progressDialog(Activity activity, boolean isCancel) {
        ProgressDialog dialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
        View view = dialog.getLayoutInflater().inflate(R.layout.dialog_loading, null);
        dialog.setCancelable(isCancel);

        dialog.show();

        dialog.setContentView(view);

        return dialog;
    }


    public static void showDialog(Activity activity, String title, String content, String okTip, View.OnClickListener onClickListener) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_normal_tip);

        TextView tvTitle = window.findViewById(R.id.tv_title_tip);
        Button ok = window.findViewById(R.id.btn_makesure);
        Button cancel = window.findViewById(R.id.btn_cancel);
        TextView tvContent = window.findViewById(R.id.tv_dialog_content);
        tvTitle.setText(title);
        ok.setText(okTip);
        tvContent.setText(content);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public static void showDialog(Activity activity, String content, String okTip) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_normal_tip);

        Button ok = window.findViewById(R.id.btn_makesure);
        Button cancel = window.findViewById(R.id.btn_cancel);
        TextView tvContent = window.findViewById(R.id.tv_dialog_content);

        ok.setText(okTip);

        tvContent.setText(content);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        cancel.setVisibility(View.GONE);
    }

    public static void showDialog(Activity activity, String content, View.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_normal_tip);

        Button ok = window.findViewById(R.id.btn_makesure);
        Button cancel = window.findViewById(R.id.btn_cancel);
        TextView tvTip = window.findViewById(R.id.tv_title_tip);
        TextView tvContent = window.findViewById(R.id.tv_dialog_content);

        tvContent.setText(content);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                alertDialog.dismiss();
            }
        });

        tvTip.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }


    public static boolean isDialogShowing(ProgressDialog dialog) {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public static void cancelProgressDialog(ProgressDialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void showDialog(Activity activity, String content, String okTip, View.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_normal_tip);

        Button ok = window.findViewById(R.id.btn_makesure);
        Button cancel = window.findViewById(R.id.btn_cancel);
        TextView tvContent = window.findViewById(R.id.tv_dialog_content);
        ok.setText(okTip);
        tvContent.setText(content);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public static AlertDialog showDialogLock(Activity activity,boolean isAll) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        // 获取布局
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_device_lock);
        TextView tvLockStatus = window.findViewById(R.id.tv_lock_status);
        TextView tvConfirm = window.findViewById(R.id.tv_dialog_cancel);
        if(isAll){
            tvLockStatus.setText("已上锁（全部）");
        }else {
            tvLockStatus.setText("已锁定");
        }
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        return alertDialog;
    }

    public static AlertDialog showDialogunLock(Activity activity,String name ,String phone,View.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        // 获取布局
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_device_unlock);
        TextView tvConfirm = window.findViewById(R.id.tv_dialog_device_unlock);
        TextView tvName = window.findViewById(R.id.tv_name);
        TextView tvPhone = window.findViewById(R.id.tv_phone);

        tvName.setText(name);
        tvPhone.setText(phone);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        return alertDialog;
    }

    public static AlertDialog showDialogunLockConfirm(Activity activity,View.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        // 获取布局
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_device_unlock_confirm);
        TextView tvConfirm = window.findViewById(R.id.tv_dialog_device_unlock_confirm);
        TextView tvCancel = window.findViewById(R.id.tv_dialog_device_cancel);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });

        return alertDialog;
    }

    public static AlertDialog showDeviceUnLock(Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        // 获取布局
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_device_unlock_tip);

        return alertDialog;
    }

    public static AlertDialog showMessageTip(Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        // 获取布局
        final Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_message_tip);
        TextView tvbackLogin = window.findViewById(R.id.tv_back_login);
        tvbackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        return alertDialog;
    }

}
