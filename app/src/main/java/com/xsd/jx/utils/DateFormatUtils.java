package com.xsd.jx.utils;

/**
 * Date: 2020/10/8
 * author: SmallCake
 */
public class DateFormatUtils {

    public static String ymd(int mYear,int mMonth,int mDay){
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        String dayStr = mDay<10?"0"+mDay:""+mDay;
        return mYear+"-"+monthStr+"-"+dayStr;
    }
    public static String ym(int mYear,int mMonth){
        String monthStr = mMonth<10?"0"+mMonth:""+mMonth;
        return mYear+"-"+monthStr;
    }
}
