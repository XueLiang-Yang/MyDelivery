package com.liang.delivery.Tools;
import com.liang.delivery.Redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MyScheduled{
    private static final Logger logger = LoggerFactory.getLogger(MyScheduled.class);
    @Resource
    private MailTool mailTool;
    @Resource
    private RedisClient redisClient;

    @Scheduled(cron = "* * 3 * * *")
    public void timeToSaveClick() {
        logger.info("开启保存点击量和评论量定时器：" + LocalDateTime.now());
    }

    @Scheduled(cron = "* * * * * *")
    public void autoDeliverMail(){
        if(redisClient.LLEN(RedisClient.MailQueue)!=0){
            logger.info("开启新信息邮件提醒定时器：" + LocalDateTime.now()+"任务开始！！！");
            String key = redisClient.LPOP(RedisClient.MailQueue);
            if(redisClient.LLEN(key)!=0){
                //从内存获取所有的值
                List<String> mails = redisClient.LRANGE(key,0,-1);
                //配置缓存队列
                LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);
                //配置线程池
                ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,10,3, TimeUnit.SECONDS, queue);
                for(String mail:mails){
                    if(queue.size()<10){
                        logger.info("开始给邮箱为："+mail+"的用户发送信息");
                        poolExecutor.execute(new MailRunnable(mail,mailTool,key));
                    }
                }
                //结束线程池
                poolExecutor.shutdown();
                redisClient.del(key);
            }
        }
        logger.info("开启新信息邮件提醒定时器：" + LocalDateTime.now()+"暂无命令");
    }

}
