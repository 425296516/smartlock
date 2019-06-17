package com.anlida.smartlock.listener;

import android.view.View;

/**
 * 点击事件防抖动（抖动时间为2000ms）
 */

public abstract class AntiShakeClickListener implements View.OnClickListener {
    private long latestClickTime;
    private static final int MIN_CLICK_DELAY = 1500;

    protected abstract void onAntiShakeClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - latestClickTime >= MIN_CLICK_DELAY) {
            latestClickTime = currentTime;
            onAntiShakeClick(v);
        }
    }
}
