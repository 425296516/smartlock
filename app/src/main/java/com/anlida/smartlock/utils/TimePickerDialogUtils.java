package com.anlida.smartlock.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * 
 * 类说明  时间选择器工具类
 *
 * @author ruiqin.shen
 * @version 2016-4-26 下午4:14:54 
 *
 */

public class TimePickerDialogUtils {

	//日期选择器
	public static void setTimePickerDialog(Context mContext, final OnSetTimeListener onSetTimeListener) {
		Calendar c = Calendar.getInstance();
		
		new TimePickerDialog(mContext,// 设置样式，四种
				new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String timewell = hourOfDay + "时" + minute + "分";
				
				onSetTimeListener.onSetTime(timewell);
				
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
	}
		
	
	public interface OnSetTimeListener {
		void onSetTime(String timewell);
	}
}
