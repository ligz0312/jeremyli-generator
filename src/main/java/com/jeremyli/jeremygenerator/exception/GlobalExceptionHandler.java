package com.jeremyli.jeremygenerator.exception;

import com.jeremyli.jeremygenerator.controller.CodeGenerateController;
import com.jeremyli.jeremygenerator.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类：处理所有controller层的异常
 * 三种指定异常的方式：
 *  1、指定注解：annotations = RestController.class
 *  2、指定包：basePackages = "com.jeremyli.jeremygenerator"
 *  3、指定类：basePackageClasses = {UserController.class}
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result<Object> handleValidationExceptions(Exception ex) {
        logger.error("[handleValidationExceptions]", ex);
//        StringBuilder sb = new StringBuilder();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((org.springframework.validation.FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            sb.append(fieldName).append(":").append(errorMessage).append(";");
//        });
        return Result.error(ex.getMessage());
    }

}
