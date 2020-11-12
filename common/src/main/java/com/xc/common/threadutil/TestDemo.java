package com.xc.common.threadutil;

import com.alibaba.druid.support.json.JSONUtils;
import com.xc.common.threadutils.ThreadTaskDemo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class TestDemo {

    public static void main(String[] args) throws InterruptedException {
        test3();
        //test2();
        //test1();
    }
    //获取全部线程的执行结果
    private static void test3() throws InterruptedException {
        Set<Callable<Object>> callables=new HashSet<>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int x=0;x<10;x++){
            callables.add(new CallableDemo(x+""));
        }

        List<Future<Object>> futures =executorService.invokeAll(callables);
        for(Future f:futures){
            show(f);
        }
        System.out.println("end1");
        executorService.shutdown();
        System.out.println("end2");

    }
    //各自返回结果
    private static void test2(){
        List<Future> futures=new ArrayList<Future>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int x=0;x<10;x++){
            Future future=executorService.submit(new CallableDemo(x+""));
            show(future);
            futures.add(future);
        }
        System.out.println("end1");
        executorService.shutdown();
        System.out.println("end2");

    }
    private static void test1(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int x=0;x<10;x++){
            executorService.submit(new RunnableDemo(x+""));
        }
        System.out.println("end1");
        executorService.shutdown();
        System.out.println("end2");
    }


    private static void show(Future future){
        try {
            //返回线程处理结果
            System.out.println("线程结果"+JSONUtils.toJSONString(future.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
