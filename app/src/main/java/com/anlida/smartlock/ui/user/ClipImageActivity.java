package com.anlida.smartlock.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.widget.ClipView;
import com.anlida.smartlock.widget.ClipViewLayout;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends FMActivity {
    @BindView(R.id.clipViewLayout)
    ClipViewLayout clipViewLayout;


    @Override
    public void initData(Bundle savedInstanceState) {
        setTitleText(getString(R.string.pic_clipimg_title));

    }

    @Override
    public void initView(Bundle bundle) {
        String clipType = getIntent().getStringExtra("CLIP_TYPE");
        if("rectangle".equals(clipType)) {
            clipViewLayout.setClipType(ClipView.ClipType.RECTANGLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        clipViewLayout.setImageSrc(getIntent().getData());
    }

    @Override
    protected String description() {
        return null;
    }

    @OnClick({R.id.btn_cancel, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.bt_ok:
                generateUriAndReturn();
                break;
        }
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = clipViewLayout.clip();
        if (zoomedCropBitmap == null) {
            LogUtils.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                LogUtils.e("android", "Cannot open file: " + ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.activity_clip_pic;
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }
}

