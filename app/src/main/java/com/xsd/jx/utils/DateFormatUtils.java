package com.xsd.jx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date: 2020/10/8
 * author: SmallCake
 */
public class DateFormatUtils {
    //当前年月日
    public static String getCurrentYmd(){
        Calendar c = Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR);
        int mMonth = c.get(java.util.Calendar.MONTH) + 1;
        int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        String dayStr = mDay<10?"0"+mDay:""+mDay;
        return mYear+monthStr+dayStr;
    }
    //当前年月
    public static String getCurrentYm(){
        Calendar c = Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR);
        int mMonth = c.get(java.util.Calendar.MONTH) + 1;
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        return mYear+monthStr;
    }
    public static String ymd(int mYear,int mMonth,int mDay){
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        String dayStr = mDay<10?"0"+mDay:""+mDay;
        return mYear+"-"+monthStr+"-"+dayStr;
    }
    public static String ym(int mYear,int mMonth){
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        return mYear+"-"+monthStr;
    }
    public static Calendar strToCalendar(String ymd){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(ymd);
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

}
