package com.wdl.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author:wudl
 * @creat 2022/10/11 17:43
 * @name reggie
 */

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.info("异常处理器 处理了: {}", exception.getMessage());
        String message = exception.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            return R.error(split[2] + "已存在");
        }


        return R.error("未知错误");
    }

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }
}
