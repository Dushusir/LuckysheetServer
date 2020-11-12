package com.xc.common.threadutil;

import com.xc.common.threadutils.ThreadRunningContextDemo;

/**
 * 不带返回值
 */
public class RunnableDemo implements Runnable {
    String taskId;
    public RunnableDemo(String _taskId){
        taskId=_taskId;
    }
    @Override
    public void run() {
        String threadName=Thread.currentThread().getName()+"--taskId: "+taskId;
        ThreadRunningContextDemo.run(threadName);
    }
}
