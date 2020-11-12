package com.xc.common.threadutils;


import com.xc.common.utils.DateUtil;
import com.xc.common.utils.RandomUtil;

/**
 * 线程测试任务
 * @author Administrator
 */
public class ThreadTaskDemo implements Runnable{

    String taskId;
    public ThreadTaskDemo(String taskId) {
        this.taskId=taskId;
    }

    @Override
    public void run() {
        String threadName=Thread.currentThread().getName()+"--taskId: "+taskId;
        ThreadRunningContextDemo.run(threadName);
    }
}
