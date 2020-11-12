package com.xc.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.Line;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * JOB时间计算
 */
@Slf4j
public class JobRunCalculation {

    /**
     * 按频率计算下一次执行时间
     * @param type 0频率 其他定时
     * @param startTime 开始时间 年月日时分秒
     * @param endTime 结束时间  年月日时分秒
     * @param lastTime 上一次时间 年月日时分秒
     * @param ruleStr 频率 例如 02:30 或者 13:01,02:30,8:00,2:35
     * @return
     */
    public static Map<String,Object> calculationTime(int type,Date startTime,Date endTime,Date lastTime,String ruleStr){
        return calculationTime(type,LocalDateTimeUtils.convertDateToLDT(startTime),LocalDateTimeUtils.convertDateToLDT(endTime),LocalDateTimeUtils.convertDateToLDT(lastTime),ruleStr);
    }
    public static Map<String,Object> calculationTime(int type,LocalDateTime startTime,LocalDateTime endTime,LocalDateTime lastTime,String ruleStr){
        log.info("时间 {} ~ {}",LocalDateTimeUtils.parseTime(startTime),LocalDateTimeUtils.parseTime(endTime));
        log.info("上一次时间 {},规则 {}",LocalDateTimeUtils.parseTime(lastTime),ruleStr);

        //获取当前时间
        LocalDateTime currentTime=LocalDateTime.now();
        //下一次开始时间
        LocalDateTime nextTime=null;
        //比较当前时间与开始时间
        long sTimeCompare=LocalDateTimeUtils.betweenTwoTime(currentTime, startTime, ChronoUnit.SECONDS);
        if(sTimeCompare>=0){
            //当前时间小于等于开始时间
            if(type==0){
                //频率,用开始时间作为下一次执行时间
                nextTime=startTime;
            }else{
                //定时
                nextTime=getNextTimeByFixedTime(startTime,ruleStr);
            }
        }else{
            //当前时间大于开始时间
            if(type==0){
                //频率 按循环计算下一次执行时间
                if(lastTime==null){
                    nextTime=currentTime;
                }else{
                    nextTime=getNextTimeByRate(lastTime,ruleStr);
                    //如果下一次时间比当前时间小，给出当前时间
                    long b=LocalDateTimeUtils.betweenTwoTime(nextTime, currentTime, ChronoUnit.SECONDS);
                    if(b>=0){
                        nextTime=currentTime;
                    }
                }
            }else{
                //定时
                LocalDateTime runTime=lastTime==null?LocalDateTime.now():lastTime;
                if(LocalDateTimeUtils.betweenTwoTime(runTime, currentTime, ChronoUnit.SECONDS)>=0){
                    runTime=currentTime;
                }
                nextTime=getNextTimeByFixedTime(runTime,ruleStr);

            }
        }
        //比较下一次执行时间与结束时间
        long eTimeCompare=LocalDateTimeUtils.betweenTwoTime(nextTime, endTime, ChronoUnit.SECONDS);
        if(eTimeCompare<0){
            //比结束时间小，停止
            return getResult(lastTime,null,false);
        }

        return getResult(lastTime,nextTime,true);
    }


    /**
     * 按循环处理，得到下一次执行时间
     * @param time 上一次时间
     * @param rateStr  循环时间格式  02:30
     * @return
     */
    private static LocalDateTime getNextTimeByRate(LocalDateTime time,String rateStr){
        //分离出小时与分钟
        int hour=Integer.parseInt(rateStr.split(":")[0]);
        int minute=Integer.parseInt(rateStr.split(":")[1]);
        if(hour>0){
            time=LocalDateTimeUtils.plus(time,hour,ChronoUnit.HOURS);
        }
        if(minute>0){
            time=LocalDateTimeUtils.plus(time,minute,ChronoUnit.MINUTES);
        }
        return time;
    }

    /**
     * 按固定时间处理,得到下一次执行时间
     * @param time 上一次时间
     * @param fixedTimeStr  定时时间格式  13:01,02:30,8:00
     * @return
     */
    private static LocalDateTime getNextTimeByFixedTime(LocalDateTime time,String fixedTimeStr){
        //下一次时间
        LocalDateTime nextTime=null;
        //用上一次时间作为基础
        List<LocalDateTime> times=getFixedTime(time,fixedTimeStr);
        for(LocalDateTime l:times){
            long b=LocalDateTimeUtils.betweenTwoTime(time, l, ChronoUnit.SECONDS);
            if(b>=0){
                nextTime=l;
                break;
            }
        }
        if(nextTime==null){
            //得到第二天第一个时间点
            time=LocalDateTimeUtils.plus(time,1,ChronoUnit.DAYS);
            times=getFixedTime(time,fixedTimeStr);
            nextTime=times.get(0);
        }
        return nextTime;
    }

    /**
     * 按给定的日期组装定时时间
     * @param time 基础日期
     * @param fixedTimeStr  定时时间格式  13:01,02:30,8:00
     * @return
     */
    private static List<LocalDateTime> getFixedTime(LocalDateTime time,String fixedTimeStr){
        String[] list=fixedTimeStr.split(",");
        List<LocalDateTime> times=new ArrayList<>(list.length);
        for(String s:list){
            int hour=Integer.parseInt(s.split(":")[0]);
            int minute=Integer.parseInt(s.split(":")[1]);
            LocalDateTime t= LocalDateTime.of(time.getYear(),time.getMonth(),time.getDayOfMonth(),hour,minute,0);
            times.add(t);
        }
        return times;
    }

    /**
     * 返回对象
     * @param lastTime 上一次执行时间
     * @param nextTime 下一次执行后死机
     * @param isRun 是否运行
     * @return
     */
    private static Map<String,Object> getResult(LocalDateTime lastTime,LocalDateTime nextTime,Boolean isRun){
        Map<String,Object> map=new HashMap<>(4);
        map.put("lastTime",LocalDateTimeUtils.convertLDTToDate(lastTime));
        map.put("nextTime",LocalDateTimeUtils.convertLDTToDate(nextTime));
        map.put("isRun",isRun);
        log.info(JsonUtil.toJsonByDateTime(map));
        return map;
    }

    /**
     * 固定时间排序（并去除非法格式）,以及检测是否5分钟间隔
     * @param fixedTimeStr 定时时间格式  13:01,02:30,8:00
     * @param checkTime true判断是否间隔5分钟 false不判断
     * @return
     */
    public static String fixedTimeOrder(String fixedTimeStr,boolean checkTime){
        LocalDateTime currentTime=LocalDateTime.now();
        List<LocalDateTime> times=getFixedTime(currentTime,fixedTimeStr);
        Collections.sort(times,new LocalDateTimeUtils());
        //检测时间
        if(checkTime&&times.size()>=2){
            for(int x=1;x<times.size();x++){
                long b=LocalDateTimeUtils.betweenTwoTime(times.get(x-1), times.get(x), ChronoUnit.SECONDS);
                if(b<=5*60){
                    throw new RuntimeException("定时任务时间间隔不能小于5分钟");
                }
            }
        }
        StringBuilder _sb=new StringBuilder(20);
        for(LocalDateTime l:times){
            if(_sb.length()>0){
                _sb.append(",");
            }
            _sb.append(
                    (l.getHour()<=9?"0"+l.getHour():l.getHour())+":"+
                    (l.getMinute()<=9?"0"+l.getMinute():l.getMinute())
            );
        }
        return _sb.toString();
    }

    public static void main(String[] args){
        LocalDateTime startTime = LocalDateTime.of(2020, 7, 28, 00, 00,00);
        LocalDateTime endTime = LocalDateTime.of(2020, 7, 30, 16, 59,59);
        LocalDateTime lastTime = LocalDateTime.of(2020, 7, 30, 11, 00,00);
        String ruleStr="2:00";
        Map<String,Object> map=calculationTime(0,startTime,endTime,null,ruleStr);
        System.out.println("===========================================================================");
        startTime = LocalDateTime.of(2020, 8, 28, 2, 45,00);
        endTime = LocalDateTime.of(2020, 8, 30, 16, 59,59);
        lastTime = LocalDateTime.of(2020, 8, 30, 11, 00,00);
        ruleStr="2:00";
        map=calculationTime(0,startTime,endTime,null,ruleStr);
        System.out.println("===========================================================================");
        startTime = LocalDateTime.of(2020, 7, 28, 00, 00,00);
        endTime = LocalDateTime.of(2020, 7, 30, 20, 59,59);
        lastTime = LocalDateTime.of(2020, 7, 30, 14, 18,00);
        ruleStr="2:00";
        map=calculationTime(0,startTime,endTime,lastTime,ruleStr);
        System.out.println("===========================================================================");


        LocalDateTime startTime2 = LocalDateTime.of(2020, 7, 28, 00, 00,00);
        LocalDateTime endTime2 = LocalDateTime.of(2020, 7, 30, 16, 59,59);
        LocalDateTime lastTime2 = LocalDateTime.of(2020, 7, 30, 11, 00,00);
        String ruleStr2="13:00,2:00,16:00,16:01";
        ruleStr2=fixedTimeOrder(ruleStr2,true);
        Map<String,Object> map2=calculationTime(1,startTime2,endTime2,lastTime2,ruleStr2);
        System.out.println("===========================================================================");
        startTime2 = LocalDateTime.of(2020, 8, 1, 00, 00,00);
        endTime2 = LocalDateTime.of(2020, 8, 30, 16, 59,59);
        lastTime2 = LocalDateTime.of(2020, 7, 30, 11, 00,00);
        ruleStr2="13:00,2:00,16:00";
        ruleStr2=fixedTimeOrder(ruleStr2,true);
        map2=calculationTime(1,startTime2,endTime2,null,ruleStr2);
        System.out.println("===========================================================================");


        //test();
    }
    private static void test(){
        String fixedTimeStr="13:01,02:30,8:00,2:35";
        fixedTimeStr=fixedTimeOrder(fixedTimeStr,true);
        System.out.println(fixedTimeStr);

        String rateStr="04:12";
        int hour=Integer.parseInt(rateStr.split(":")[0]);
        int minute=Integer.parseInt(rateStr.split(":")[1]);

        LocalDateTime ldt=LocalDateTime.now();
        ldt= LocalDateTime.of(2020, 12, 31, 20, 50,40);
        System.out.println(LocalDateTimeUtils.parseTime(ldt));
        ldt=getNextTimeByRate(ldt,rateStr);
        System.out.println(LocalDateTimeUtils.parseTime(ldt));

        Date dd=null;
        LocalDateTime localDateTime11=LocalDateTimeUtils.convertDateToLDT(dd);


        System.out.println(ldt);
        LocalDate ld=LocalDate.now();
        System.out.println(ld);
        LocalTime lt=LocalTime.now();
        System.out.println(lt);

        //
        Date d=new Date();
        System.out.println(d);
        System.out.println(DateUtil.parseTime(d));
        LocalDateTime localDateTime=LocalDateTimeUtils.convertDateToLDT(d);
        System.out.println(LocalDateTimeUtils.parseTime(localDateTime));


        LocalDateTime start = LocalDateTime.of(2020, 11, 13, 13, 13,40);
        LocalDateTime end = LocalDateTime.of(2020, 11, 13, 13, 13,41);

        System.out.println(LocalDateTimeUtils.parseTime(start));
        System.out.println(LocalDateTimeUtils.parseTime(end));
        System.out.println("年:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.YEARS));
        System.out.println("月:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MONTHS));
        System.out.println("日:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.DAYS));
        System.out.println("半日:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.HALF_DAYS));
        System.out.println("小时:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.HOURS));
        System.out.println("分钟:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MINUTES));
        System.out.println("秒:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.SECONDS));
        System.out.println("毫秒:" + LocalDateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MILLIS));
    }






}
