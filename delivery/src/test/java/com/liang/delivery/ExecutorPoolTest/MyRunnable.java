package com.liang.delivery.ExecutorPoolTest;

import com.liang.delivery.Tools.MyScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MyRunnable.class);
    @Override
    public void run() {
        logger.info(Thread.currentThread().getName()+"模拟邮件发送");
        System.out.println(Thread.currentThread().getName()+"模拟邮件发送");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName()+"模拟邮件发送");
            logger.info(Thread.currentThread().getName()+"邮件发送成功");
        }
    }
}
