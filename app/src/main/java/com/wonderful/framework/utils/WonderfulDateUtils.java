package com.wonderful.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/9/1.
 */

public class WonderfulDateUtils {
    /**
     * 将时间戳转化成固定格式（默认 yyyy-MM-dd  当前时间 ）
     */
    public static String getFormatTime(String format, Date date) {
        if (WonderfulStringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String formatTime = sdf.format(date);
        return formatTime;
    }

    /**
     * 将固定格式（默认 yyyy-MM-dd HH:mm:ss 当前时间 ）转化成时间戳
     */
    public static long parseFormatTime(String format, String time) throws ParseException {
        if (WonderfulStringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        Date date = sdf.parse(time);
        return date.getTime();
    }

    /**
     * 获取倒计时数组
     * SimpleDateFormat 构造方法中的Local.CHAIN 表示将时间戳格式化后 输出语言环境
     *
     * sdf.setTimeZone(TimeZone.getTimeZone("GMT")); 表示 将时间戳格式化时采用的时区
     *
     */
    public static String[] getCountDownStrings(long totalTime, int lenth) {
        String[] times = new String[lenth];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        if (totalTime > 24 * 60 * 60 * 1000) {
            totalTime -= 24 * 60 * 60 * 1000;
            String formatTime = sdf.format(new Date(totalTime));
            String[] strs = formatTime.split(":");
            int j = lenth - 1;
            for (int i = strs.length - 1; i > strs.length - lenth - 1; i--) {
                times[j] = strs[i];
                j--;
            }
        } else {
            String formatTime = sdf.format(new Date(totalTime));
            String[] strs = formatTime.split(":");
            int j = lenth;
            for (int i = strs.length - 1; i > strs.length - lenth - 1; i--) {
                times[j] = strs[i];
                j--;
            }
            if (lenth >= 4) {
                times[3] = "00";
            }
        }
        return times;
    }
}
