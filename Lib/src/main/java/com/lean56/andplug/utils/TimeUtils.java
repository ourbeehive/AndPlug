package com.lean56.andplug.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.*;

/**
 * Utilities for dealing with dates and times
 *
 * @author Charles
 */
public class TimeUtils {

    public static CharSequence formatString(String dateStr) {
        return formatString(dateStr, "yyyy年MM月dd日");
    }

    public static CharSequence formatDate(Date date) {
        return formatDate(date, "yyyy年MM月dd日");
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
