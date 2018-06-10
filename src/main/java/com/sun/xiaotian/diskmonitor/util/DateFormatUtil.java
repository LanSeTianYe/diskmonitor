package com.sun.xiaotian.diskmonitor.util;


import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DateFormatUtil {

    private static final Map<Long, Date> dateCache = new HashMap<>();

    public Date format(Date date) {
        long key = date.getTime();
        if (null == dateCache.get(key)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            dateCache.put(key, calendar.getTime());
        }
        return dateCache.get(key);
    }
}
