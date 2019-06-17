package com.anlida.smartlock.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class TimeFormatUtils {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    public TimeFormatUtils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 获取两个日期之间的间隔天数
     * @param startDate
     * @param endDate
     * @return
     */
    public int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取两个日期之间的间隔月份数
     * @param olderDate
     * @param newerDate
     * @return
     * @throws IllegalArgumentException
     */
    public int[] getGapMonth(Date olderDate, Date newerDate) throws IllegalArgumentException {
        int[] gapMonthh = new int[2];
        int years = 0;
        int months = 0;
        int days = 0;

        Calendar older = Calendar.getInstance();
        Calendar newer = Calendar.getInstance();
        older.setTime(olderDate);
        newer.setTime(newerDate);

        if (olderDate.getTime() > newerDate.getTime()) {
            throw new IllegalArgumentException();
        } else {
            years = newer.get(Calendar.YEAR) - older.get(Calendar.YEAR);
            if (years >= 0) {
                months = newer.get(Calendar.MONTH) - older.get(Calendar.MONTH) + 12 * years;
                older.add(Calendar.MONTH, months);
                days = newer.get(Calendar.DATE) - older.get(Calendar.DATE);
                if (days < 0) {
                    months = months - 1;
                    older.add(Calendar.MONTH, -1);
                }
                days = getGapCount(older.getTime(), newer.getTime());
                gapMonthh[0] = months;
                gapMonthh[1] = days;
            }

        }
        return gapMonthh;
    }

    /**
     * 获取当前日期(年-月-日)
     * @return
     */
    public String getSimpDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = Calendar.getInstance().getTime();
        String curTime = formatter.format(currentDate);

        return curTime;
    }

    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = Calendar.getInstance().getTime();
        String curTime = formatter.format(currentDate);
        return curTime;
    }

    /**
     * String 转 Date
     * @param strDate
     * @return
     */
    public Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

}
