package com.anlida.smartlock.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.anlida.smartlock.R;


/**
 * Created by (张慈瑞).
 */

public class CountDownUtils {

    public static CountDownTimer mCountDownTimer;

    public static void startCountDown(Context context, TextView captchaView) {
        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (null != captchaView) {
                    captchaView.setText(String.format(context.getString(R.string.captcha_text),millisUntilFinished / 1000+""));
                    captchaView.setEnabled(false);
                    captchaView.setAlpha(0.6f);
                }

            }

            @Override
            public void onFinish() {
                if (null != captchaView) {
                    captchaView.setText(context.getString(R.string.get_verification_code_again));
                    captchaView.setEnabled(true);
                    captchaView.setAlpha(1);
                }
            }
        }.start();
    }

    /**
     * 停止验证码计时器
     */
    public static void stopCountdown(Context context, TextView captchaView) {
        if (null != mCountDownTimer) {
            mCountDownTimer.onFinish();
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        if (null != captchaView) {
            captchaView.setEnabled(true);
            captchaView.setText(context.getString(R.string.get_verification_code));
            captchaView.setAlpha(1);
        }
    }
}
