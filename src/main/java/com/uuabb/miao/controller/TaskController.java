package com.uuabb.miao.controller;

import com.uuabb.miao.entity.DeferredResultEntity;
import com.uuabb.miao.entity.ResponseMsg;
import com.uuabb.miao.utils.TaskQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by brander on 2018/11/20
 */
@RestController
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //超时结果
    private static final ResponseMsg<String> OUT_OF_TIME_RESULT = new ResponseMsg<>(-1, "超时", "out of time");

    //超时时间
    private static final long OUT_OF_TIME = 3000L;

    @Autowired
    private TaskQueue taskQueue;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getResult() {

        log.info("接收请求，开始处理...");

        //建立DeferredResult对象，设置超时时间，以及超时返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);

        result.onTimeout(() -> {
            log.info("调用超时");
        });

        result.onCompletion(() -> {
            log.info("调用完成");
        });

        //并发，加锁
        synchronized (taskQueue) {

            taskQueue.put(result);

        }

        log.info("接收任务线程完成并退出");

        return result;
    }

    @GetMapping("/deferred")
    public DeferredResult<Object> testDeferredResult() {
        log.info("主线程开始！");
        DeferredResult<Object> result = new DeferredResult<Object>();
        DeferredResultEntity deferredResultEntity = new DeferredResultEntity();
        deferredResultEntity.setResult(result);
        deferredResultEntity.setFlag(true);
        log.info("主线程结束！");
        return result;
    }
}
