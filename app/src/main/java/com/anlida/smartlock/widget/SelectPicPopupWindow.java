package com.anlida.smartlock.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anlida.smartlock.R;


/**
 * Created by (张慈瑞).
 */
public class SelectPicPopupWindow extends PopupWindow {

    private TextView photoBtn, cameraBtn, cancelBtn;
    private View mMenuView;

    @SuppressLint("InflateParams")
    public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_pic_popwindow, null);
        photoBtn = mMenuView.findViewById(R.id.pic_photo_btn);
        cameraBtn = mMenuView.findViewById(R.id.pic_camera_btn);
        cancelBtn = mMenuView.findViewById(R.id.pic_cancel_btn);
        // 设置按钮监听
        photoBtn.setOnClickListener(itemsOnClick);
        cameraBtn.setOnClickListener(itemsOnClick);
        cancelBtn.setOnClickListener(itemsOnClick);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.5f;
        context.getWindow().setAttributes(params);
        // 此行代码主要是解决在华为手机上半透明效果无效的bug
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                // 不移除该Flag的话,可能出现黑屏的bug
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(params);
                dismiss();
            }
        });
    }
}
