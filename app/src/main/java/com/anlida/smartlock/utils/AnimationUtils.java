package com.anlida.smartlock.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangcirui on 2018/9/19.
 */

public class AnimationUtils {


    public static void setFlickerAnimation(ImageView iv_chat_head) {
        final Animation animation = new TranslateAnimation(0, 0, 25f, 5f);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        // 表示重复多次
        animation.setRepeatCount(Animation.INFINITE);
        // 表示动画结束后，反过来再执行。RESTART表示从头开始，REVERSE表示从末尾倒播。
        animation.setRepeatMode(Animation.REVERSE);
        iv_chat_head.setAnimation(animation);
    }


    public static void setFlickerRotateAnimation(TextView iv_chat_head) {
        final Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(700);
        animation.setInterpolator(new LinearInterpolator());
        // 表示重复多次
        animation.setRepeatCount(1);
        // 表示动画结束后，反过来再执行。RESTART表示从头开始，REVERSE表示从末尾倒播。
        animation.setRepeatMode(Animation.RESTART);
        iv_chat_head.setAnimation(animation);
    }

    /**
     * 开启View闪烁效果
     */

    public static void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.3f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 取消View闪烁效果
     */
    public static void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }
}
