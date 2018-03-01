package com.uuabb.miao.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brander on 2018/3/1
 */
@ControllerAdvice
public class GlobeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String ,Object> exceptionHandler(HttpServletRequest res,Exception e){
        Map<String , Object> map=new HashMap<>();
        map.put("success", false);
        map.put("msg",e.getMessage());
        return map;
    }
}
