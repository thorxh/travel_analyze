package com.bonc.usdp.util;

import com.bonc.usdp.system.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br>Created by liyanjun@bonc.com.cn on 2017/9/21.<br><br>
 */
public class TimeUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(Config.SYSTEM_PARAM_PATTERN_DATE);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat(Config.SYSTEM_PARAM_PATTERN_TIME);

    public static Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseTime(String dateStr) {
        try {
            return timeFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

}
