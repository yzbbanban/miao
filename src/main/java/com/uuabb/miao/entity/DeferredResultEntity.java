package com.uuabb.miao.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by brander on 2018/11/20
 */
@Component
public class DeferredResultEntity {
    /**
     * 后续监听器中用于判断
     */
    private boolean flag = false;

    /**
     * 用于存储DeferredResult
     */
    private DeferredResult<Object> result;

    public DeferredResult<Object> getResult() {
        return result;
    }

    public void setResult(DeferredResult<Object> result) {
        this.result = result;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
