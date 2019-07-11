package com.anlida.smartlock.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.listener.OnSelectBloodTypeListener;

public class BloodTypePopupWindow extends PopupWindow {

    private View mMenuView;
    private TextView tvA, tvB, tvAB, tvO;
    private OnSelectBloodTypeListener mlistener;

    @SuppressLint("InflateParams")
    public BloodTypePopupWindow(Activity context, OnSelectBloodTypeListener listener) {
        super(context);
        mlistener = listener;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_bloodtype_popupwindow, null);
        tvA = mMenuView.findViewById(R.id.tv_blood_type_a);
        tvB = mMenuView.findViewById(R.id.tv_blood_type_b);
        tvAB = mMenuView.findViewById(R.id.tv_blood_type_ab);
        tvO = mMenuView.findViewById(R.id.tv_blood_type_o);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.alpha = 0.5f;
        context.getWindow().setAttributes(params);
        // 此行代码主要是解决在华为手机上半透明效果无效的bug
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                listener.onDismiss();
                params.alpha = 1.0f;
                // 不移除该Flag的话,可能出现黑屏的bug
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(params);
                dismiss();
            }
        });

        tvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onSelect("A");
                dismiss();
            }
        });

        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onSelect("B");
                dismiss();
            }
        });

        tvAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onSelect("AB");
                dismiss();
            }
        });

        tvO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onSelect("O");
                dismiss();
            }
        });

    }

}
