package com.xc.common.threadutils;


import com.xc.common.utils.DateUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程示例
 * ScheduledThreadPoolExecutor可以用来在给定延时后执行异步任务或者周期性执行任务，
 * 相对于任务调度的Timer来说，其功能更加强大，Timer只能使用一个后台线程执行任务，
 * 而ScheduledThreadPoolExecutor则可以通过构造函数来指定后台线程的个数。
 * @author Administrator
 */
public class TestScheduledThreadPoolExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(DateUtil.getNowDateFormate()+" start");
        //限制程序执行的时间
        awaitTermination();
        //执行一次
        //runOnce();
        //无限定时循环执行
        //scheduleAtFixedRate();
        //测试
        //test1();
    }

    /**
     * 限制程序执行的时间
     */
    private static void awaitTermination() throws InterruptedException {
        ScheduledThreadPoolExecutor exec=new ScheduledThreadPoolExecutor(1,new ThreadFactoryDemo());
        //2表示首次执行任务的延迟时间，7表示每次执行任务的间隔时间，TimeUnit.SECONDS 执行的时间间隔数值单位
        exec.scheduleAtFixedRate(new ThreadTaskDemo("1"),1,5,TimeUnit.SECONDS);
        //20秒后结束
        exec.awaitTermination(20,TimeUnit.SECONDS);
        exec.shutdown();
        System.out.println("end");
    }
    /**
     * 执行一次
     */
    private static void runOnce(){
        ScheduledThreadPoolExecutor exec=new ScheduledThreadPoolExecutor(1,new ThreadFactoryDemo());
        //2表示首次执行任务的延迟时间，7表示每次执行任务的间隔时间，TimeUnit.SECONDS 执行的时间间隔数值单位
        exec.schedule(new ThreadTaskDemo("1"),5,TimeUnit.SECONDS);
        exec.shutdown();
        System.out.println("end");
    }
    /**
     * 无限定时循环执行
     */
    private static void scheduleAtFixedRate(){
        ScheduledThreadPoolExecutor exec=new ScheduledThreadPoolExecutor(1,new ThreadFactoryDemo());
        //2表示首次执行任务的延迟时间，7表示每次执行任务的间隔时间，TimeUnit.SECONDS 执行的时间间隔数值单位
        exec.scheduleAtFixedRate(new ThreadTaskDemo("1"),2,7,TimeUnit.SECONDS);
        //exec.shutdown();
        System.out.println("end");
    }
    /**
     * 测试
     */
    private static void test1(){
        //ScheduledThreadPoolExecutor exec=new ScheduledThreadPoolExecutor(3);
        ScheduledThreadPoolExecutor exec=new ScheduledThreadPoolExecutor(3,new ThreadFactoryDemo());
        for(int i=1;i<=5;i++){
            exec.submit(new ThreadTaskDemo(i+""));
        }
        exec.shutdown();
        System.out.println("end");
    }
}
