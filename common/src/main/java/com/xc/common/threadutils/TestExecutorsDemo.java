package com.xc.common.threadutils;

import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 多线程示例
 * @author Administrator
 */
public class TestExecutorsDemo {

    public static void main(String[] args){

        System.out.println("start");

        //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。（线程最大并发数不可控制）
        //newCachedThreadPool();
        //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        //newFixedThreadPool();
        //创建一个定长线程池，支持定时及周期性任务执行。
        //newScheduledThreadPool();

    }
    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。（线程最大并发数不可控制）
     */
    private static void newCachedThreadPool(){
        ExecutorService exec=Executors.newCachedThreadPool(new ThreadFactoryDemo());
        for(int i=1;i<=3;i++) {
            exec.submit(new ThreadTaskDemo(i+""));
        }
        exec.shutdown();
        System.out.println("end");
    }
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     */
    private static void newFixedThreadPool(){
        ExecutorService exec=Executors.newFixedThreadPool(1,new ThreadFactoryDemo());
        for(int i=1;i<=3;i++) {
            exec.submit(new ThreadTaskDemo(i+""));
        }
        exec.shutdown();
        System.out.println("end");
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行。
     */
    private static void newScheduledThreadPool(){
        ExecutorService exec=Executors.newScheduledThreadPool(1,new ThreadFactoryDemo());
        for(int i=1;i<=3;i++) {
            exec.submit(new ThreadTaskDemo(i+""));
        }
        exec.shutdown();
        System.out.println("end");
    }

}

/**
 ThreadPoolExecutor的构造方法
public ThreadPoolExecutor(int corePoolSize,//核心线程池大小
                          int maximumPoolSize,//最大线程池大小
                          long keepAliveTime,//线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut(true)成为核心线程的有效时间
                          TimeUnit unit,//keepAliveTime的时间单位
                          BlockingQueue<Runnable> workQueue,//阻塞任务队列
                          ThreadFactory threadFactory,//线程工厂
                          RejectedExecutionHandler handler) {//当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
    if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}

 1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
 2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
 3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务
 4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
 5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
 6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭

 使用ThreadPoolExecutor的参数后，我们就可以不用局限于最上面那四种线程池，可以按照需要来构建自己的线程池；

 还有一点，通过ThreadFactory可以实现对线程的命名，举例：

executor = Executors.newCachedThreadPool(new ThreadFactory() {

        final AtomicInteger threadNumber = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
        // Use our own naming scheme for the threads.
        Thread thread = new Thread(
                Thread.currentThread().getThreadGroup(),
                runnable,
                "pool-spark" + threadNumber.getAndIncrement(), 0); //这里实现命名
                // Make workers daemon threads.
                thread.setDaemon(true
        );
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
            return thread;
        }
});

 */