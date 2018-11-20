package com.uuabb.miao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by brander on 2018/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int taskId;

    private DeferredResult<ResponseMsg<String>> taskResult;


    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskResult" + "{responseMsg=" + taskResult.getResult() + "}" +
                '}';
    }
}
