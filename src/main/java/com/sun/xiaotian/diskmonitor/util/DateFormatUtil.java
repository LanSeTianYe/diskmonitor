package com.sun.xiaotian.diskmonitor.util;


import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormatUtil {

    public Date format(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }
}
