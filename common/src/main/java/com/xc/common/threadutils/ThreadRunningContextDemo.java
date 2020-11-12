package com.xc.common.threadutils;


import com.xc.common.utils.DateUtil;
import com.xc.common.utils.RandomUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程执行内容
 */
public class ThreadRunningContextDemo {

    public static Map run(String threadName){
        Map map=new HashMap(4);
        try {
            long l=RandomUtil.getRandom(1,10)*1000L;
            map.put("name",threadName);
            map.put("time",l);

            System.out.println(DateUtil.getNowDateFormate()+" "+ threadName+" => start "+l);
            Thread.sleep(l);
            System.out.println(DateUtil.getNowDateFormate()+" "+ threadName+" => end "+ l);
            map.put("status","success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map.put("status","error:"+e.getMessage());
        }
        return map;
    }
}
