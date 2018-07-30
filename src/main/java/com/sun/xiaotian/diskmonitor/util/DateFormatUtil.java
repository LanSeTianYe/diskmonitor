package com.sun.xiaotian.diskmonitor.util;

import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    public static Date format(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
