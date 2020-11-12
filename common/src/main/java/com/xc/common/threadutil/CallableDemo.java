package com.xc.common.threadutil;

import com.xc.common.threadutils.ThreadRunningContextDemo;
import com.xc.common.utils.DateUtil;
import com.xc.common.utils.RandomUtil;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 带返回值
 */
public class CallableDemo implements Callable {
    String taskId;
    public CallableDemo(String _taskId){
        taskId=_taskId;
    }

    @Override
    public Object call() throws Exception {
        String threadName=Thread.currentThread().getName()+"--taskId: "+taskId;
        Map m=ThreadRunningContextDemo.run(threadName);
        return m;
    }
}
