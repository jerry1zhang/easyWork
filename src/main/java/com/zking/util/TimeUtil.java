package com.zking.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date getAddDay(int addnum){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addnum);
        date = calendar.getTime();
        return date;
    }
}
