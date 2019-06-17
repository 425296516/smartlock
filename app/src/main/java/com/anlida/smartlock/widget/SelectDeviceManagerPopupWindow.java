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
import com.anlida.smartlock.listener.OnSelectListener;
import com.anlida.smartlock.model.resp.RespDeviceManager;

public class SelectDeviceManagerPopupWindow extends PopupWindow {

    private View mMenuView;
    private Activity activity;
    private TextView tvBloodType,tvAge,tvSex,tvWorkId;

    @SuppressLint("InflateParams")
    public SelectDeviceManagerPopupWindow(Activity context, RespDeviceManager.DataBean.ListBean listBean, OnSelectListener listener) {
        super(context);
        activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_device_manager_popupwindow, null);
        tvBloodType = mMenuView.findViewById(R.id.tv_blood_type);
        tvAge = mMenuView.findViewById(R.id.tv_age);
        tvSex = mMenuView.findViewById(R.id.tv_sex);
        tvWorkId = mMenuView.findViewById(R.id.tv_work_id);

        tvBloodType.setText(listBean.getBloodType());
        tvAge.setText(listBean.getAge());
        if(listBean.getSex()== 1){
            tvSex.setText("男");
        }else if(listBean.getSex()== 2){
            tvSex.setText("女");
        }

        tvWorkId.setText(listBean.getWorkId());

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
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
        //params.alpha = 0.5f;
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
    }


}
