package com.lean56.andplug.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.*;

/**
 * Utilities for dealing with dates and times
 *
 * @author Charles
 */
public class TimeUtils {

    private final static String DEFAULT_SDF = "MM月dd日";

    public static CharSequence formatString(String dateStr) {
        return formatString(dateStr, DEFAULT_SDF);
    }

    public static CharSequence formatDate(Date date) {
        return formatDate(date, DEFAULT_SDF);
    }

    public static CharSequence formatString(String dateStr, String sdfStr) {
        Date date = strToDate(dateStr);
        if (null != date) {
            return formatDate(date, sdfStr);
        } else {
            return "";
        }
    }

    public static CharSequence formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return formatDate(date, sdf);
    }

    public static CharSequence formatDate(Date date, SimpleDateFormat sdf) {
        try {
            return sdf.format(date);
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public static Date strToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public static long getMondayInMillis(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Get relative time for date
     *
     * @param date
     * @return relative time
     */
    public static CharSequence getRelativeTime(final Date date) {
        return getRelativeTime(date.getTime());
    }

    public static CharSequence getRelativeTime(long millis) {
        return DateUtils.getRelativeTimeSpanString(millis, System.currentTimeMillis(), SECOND_IN_MILLIS, FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR | FORMAT_NUMERIC_DATE);
    }

}
