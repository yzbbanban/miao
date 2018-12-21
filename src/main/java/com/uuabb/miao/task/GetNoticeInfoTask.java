package com.uuabb.miao.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.net.SocketTimeoutException;

/**
 * Created by ban on 2018/5/3.
 * 缓存公告信息
 */
//@Component
//@EnableScheduling
public class GetNoticeInfoTask {
    //DI
    private final static Logger logger = Logger.getLogger(GetNoticeInfoTask.class);
    //DI END

    /**
     * 缓存公告数据
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void test() throws SocketTimeoutException, InterruptedException {
        logger.info("============test===============");
        Thread.sleep(10000);
        String str = "1";
        for (int i = 0; i < 100000; i++) {
            str += i;
        }
        throw new SocketTimeoutException();
    }

    /**
     * 缓存公告数据
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void test2() throws SocketTimeoutException, InterruptedException {
        logger.info("============test2===============");
        Thread.sleep(10000);
        String str = "1";
        for (int i = 0; i < 100000; i++) {
            str += i;
        }
        throw new JedisConnectionException("xxx");
    }

}
