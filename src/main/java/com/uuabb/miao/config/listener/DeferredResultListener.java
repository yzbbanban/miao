package com.uuabb.miao.config.listener;

import com.uuabb.miao.entity.DeferredResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by brander on 2018/11/20
 */
@Component
public class DeferredResultListener implements ApplicationListener<ContextRefreshedEvent> {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeferredResultEntity deferredResultEntity;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            int count = 0;
            System.out.println("监听器");
            while (true) {
                if (deferredResultEntity.isFlag()) {
                    log.info("线程" + Thread.currentThread().getName() + "开始");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    deferredResultEntity.getResult().setResult("SUCCESS");
                    deferredResultEntity.setFlag(false);
                    log.info("线程" + Thread.currentThread().getName() + "结束");
                    break;
                } else {
                    log.info("第" + count + "检查");
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}