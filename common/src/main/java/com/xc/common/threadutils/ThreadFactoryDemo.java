package com.xc.common.threadutils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂处理类（示例）
 * @author Administrator
 */
public class ThreadFactoryDemo implements ThreadFactory {

    /**
     * AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减。
     * 返回的是当前值 threadNumber.getAndIncrement()
     */
    AtomicInteger threadNumber = new AtomicInteger(1);
    /**
     * 线程组
     */
    ThreadGroup group;
    /**
     * 前缀
     */
    String namePrefix;

    /**
     * 初始化
     */
    ThreadFactoryDemo(){
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +threadNumber.getAndIncrement() + "-thread-";
    }

    /**
     * 创建一个新线程
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        /**
         * 线程组
         * 调用的对象
         * 名称
         * 新线程所需的堆栈大小，或零表示将忽略此参数
         */
        Thread t=new Thread(group,r,namePrefix+threadNumber.getAndIncrement(),0);
        /**
         * Thread.Daemon(true) 必须在Thread.start()方法之前设置，否则会出现IllegalThreadStateException异常
         * 不能把正在运行的常规线程设置为守护线程
         * 守护线程应该永远不去访问固有资源，如：数据库、文件等。因为它会在任何时候甚至在一个操作的中间发生中断。
         */
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        /**
         * 优先级   : 只能反映 线程 的 中或者是 紧急程度 , 不能决定 是否一定先执行
         * setPriority()
         * 1~10   1最低  10最高    5是默认值
         */
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
