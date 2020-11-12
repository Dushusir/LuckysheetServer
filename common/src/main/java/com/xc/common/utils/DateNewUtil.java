package com.xc.common.utils;

import com.sun.glass.ui.Pixels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * jdk8中新日期对象
 * @author Administrator
 */
public class DateNewUtil {
    private static DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 创建一个日期类
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDate createDate(int year,int month,int day){
        return LocalDate.of(year, month, day);
    }

    /**
     * 日期变化
     * @param localDate
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDate addDate(LocalDate localDate,int year,int month,int day){
        if(localDate!=null){
            if(year!=0){
                localDate=localDate.plusYears(year);
            }
            if(month!=0){
                localDate=localDate.plusMonths(month);
            }
            if(day!=0){
                localDate=localDate.plusDays(day);
            }
        }
        return localDate;
    }
    /**
     * 返回当前日期 2019-12-25
     * @return
     */
    public static String getNowDay(){
        return LocalDate.now().toString();
    }
    public static String getNowDay(LocalDate localDate){
        return localDate.toString();
    }
    /**
     * 返回当前日期 2019年12月26日
     * @return
     */
    public static String getNowDay2(){
        LocalDate localDate= LocalDate.now();
        return localDate.getYear()+"年"+localDate.getMonthValue()+"月"+localDate.getDayOfMonth()+"日";
    }
    public static String getNowDay2(LocalDate localDate){
        return localDate.getYear()+"年"+localDate.getMonthValue()+"月"+localDate.getDayOfMonth()+"日";
    }
    /**
     * 日期是否相等
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static boolean isDayEqual(LocalDate localDate1,LocalDate localDate2){
        return localDate1.equals(localDate2);
    }


    /**
     * 周处理
     * @param localDate
     * @param weeks
     * @return
     */
    public static LocalDate addWeek(LocalDate localDate,int weeks){
        return localDate.plus(weeks,ChronoUnit.WEEKS);
    }

    /**
     * 返回当前时间
     * @return
     */
    public static String getNowTime(){
        return LocalTime.now().toString();
    }
    public static String formatTime(LocalTime localTime){
        return localTime.toString();
    }


    /**
     * 返回当前日期时间
     * @return
     */
    public static String getNowDateTime(){
        return LocalDateTime.now().toString();
    }
    public static String formatDateTime(LocalDateTime localDateTime){
        return localDateTime.format(format);
    }






    public static void main(String[] args){
        BigDecimal b =new BigDecimal("0.235");
        b=b.setScale(2,BigDecimal.ROUND_DOWN);
        System.out.println(b.floatValue());
        // 2019-12-25
        System.out.println(getNowDay());
        // 2019年12月26日
        System.out.println(getNowDay2());
        // 2019-03-21
        LocalDate localDate=createDate(2019,3,21);
        System.out.println(getNowDay(localDate));
        //日期加减
        localDate=addDate(localDate,1,2,3);
        System.out.println(getNowDay(localDate));
        //2周前
        localDate=addWeek(localDate,-2);
        System.out.println(getNowDay(localDate));
        //判断日期是否相等
        LocalDate localDate1=createDate(2019,3,21);
        LocalDate localDate2=createDate(2019,3,21);
        System.out.println(isDayEqual(localDate1,localDate2));

        // 时间
        System.out.println("===================================");
        System.out.println(getNowDateTime());
        System.out.println(formatDateTime(LocalDateTime.now()));

    }
}
