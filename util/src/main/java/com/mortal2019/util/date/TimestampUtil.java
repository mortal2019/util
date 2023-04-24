package com.mortal2019.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * SQL时间工具类
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 11:29
 */
public class TimestampUtil {
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转化为开始时间
     *
     * @param value - string
     * @return java.sql.Timestamp
     */
    public static Timestamp beginTime(String value) {
        try {
            String tempV = value;
            if (tempV.length() == FORMAT_YEAR_MONTH_DAY.length()) {
                tempV += " 00:00:00";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR.length()) {
                tempV += ":00:00";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE.length()) {
                tempV += ":00";
            }
            return Timestamp.valueOf(tempV);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 转化为结束时间
     *
     * @param value - string
     * @return java.sql.Timestamp
     */
    public static Timestamp endTime(String value) {
        try {
            String tempV = value;
            if (tempV.length() == FORMAT_YEAR_MONTH_DAY.length()) {
                tempV += " 23:59:59";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR.length()) {
                tempV += ":59:59";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE.length()) {
                tempV += ":59";
            }
            return Timestamp.valueOf(tempV);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 判端是否为时间字符串类型
     *
     * @param value - string
     * @return boolean
     */
    public static boolean isTimestampString(String value) {
        return endTime(value) != null;
    }

    public static String toString(Timestamp timestamp, String format) {
        try {
            return new SimpleDateFormat(format).format(timestamp);
        }
        catch (Exception ex) {
            return "";
        }
    }

}
