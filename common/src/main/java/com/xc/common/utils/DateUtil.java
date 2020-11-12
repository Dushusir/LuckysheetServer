package com.xc.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat DF_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat DF_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static String parseTime(Date date) {
        return DF_TIME.format(date);
    }
    public static String parseMinute(Date date) {
        return DF_MINUTE.format(date);
    }
    public static String parseDate(Date date) {
        return DF_DATE.format(date);
    }
    public static String getNowDateFormate(){
        return DF_TIME.format(new Date());
    }

}
