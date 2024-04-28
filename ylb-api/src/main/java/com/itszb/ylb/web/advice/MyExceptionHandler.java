package com.itszb.ylb.web.advice;

import com.itszb.ylb.exception.MsgTipException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.advice
 * @className: MyExceptionHandler
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/16 14:47
 * @version: 1.0
 */
//@ControllerAdvice // 对控制器的增强处理
//@ResponseBody
@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class MyExceptionHandler {
    @ExceptionHandler(MsgTipException.class)
    public Map exceptionHandler(Exception e){
        e.printStackTrace();
        Map map= new HashMap();
        map.put("success", false);
        map.put("errMsg", e.getMessage());
        return map;


    }
    @ExceptionHandler(Exception.class)
    public Map exception(Exception e) {
        e.printStackTrace();
        Map map = new HashMap();
        map.put("success", false);
        map.put("errMsg", "系统忙，请稍后再试...");
        return map;
    }
}
