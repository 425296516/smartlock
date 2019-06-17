package com.anlida.smartlock.utils;

/**
 * 防止快速点击
 */
public class QuickClickUtils {

	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static boolean isFastDoubleClick(long milliSecond) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < milliSecond) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
