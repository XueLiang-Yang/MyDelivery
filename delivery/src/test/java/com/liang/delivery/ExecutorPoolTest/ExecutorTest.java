package com.liang.delivery.ExecutorPoolTest;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {
    @Test
    public void executorTest(){
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,10,3, TimeUnit.SECONDS, queue);
        //模拟发送60份邮件
        int i = 60;
        while(i!=0){
            if(queue.size()<10){
                poolExecutor.execute(new MyRunnable());
                i--;
                System.out.println(i);
            }
        }
        //结束线程池
        poolExecutor.shutdown();
    }
}
