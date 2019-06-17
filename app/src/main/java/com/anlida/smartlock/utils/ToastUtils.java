package com.anlida.smartlock.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 吐司
 */
public class ToastUtils {

	private static Toast toast;

	/**
	 * String类型的
	 * @param mContext
	 * @param message
     */
	public static void show(Context mContext, String message) {

		if ( null == toast) {
			toast = Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();
	}


}
