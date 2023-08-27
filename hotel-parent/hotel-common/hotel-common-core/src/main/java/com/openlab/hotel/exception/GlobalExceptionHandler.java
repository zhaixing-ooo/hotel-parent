package com.openlab.hotel.exception;

import com.openlab.hotel.base.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

/**
 * @Description 统一异常处理类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义异常
    @ExceptionHandler(BadRequestException.class)
    public Result<Object> badRequestException(BadRequestException e) {
        log.error("错误的请求：" + e.getMessage());
        return Result.fail().code(HttpStatus.BAD_REQUEST.value()).message(e.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public Result<Object> validateException(Exception ex) {
        String msg = null;
        if (ex instanceof ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
            ConstraintViolation<?> next = violations.iterator().next();
            msg = next.getMessage();
        } else if (ex instanceof BindException be) {
            msg = be.getBindingResult().getFieldError().getDefaultMessage();
        }
        return Result.fail().message(msg);
    }

    /**
     * 统一处理异常，可以使用Exception.class先打印一下异常类型来确定具体异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}, 异常类型:{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer stringBuffer = new StringBuffer();
        bindingResult.getFieldErrors().forEach(item ->{
            //获取错误信息
            String message = item.getDefaultMessage();
            //获取错误的属性名字
            String field = item.getField();
            stringBuffer.append(field).append(":").append(message).append(" ");
        });
        return Result.fail().message(stringBuffer.toString());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exception(Exception e) {
        log.error("发生异常了。" + e.getMessage());
        return Result.fail().code(999).message(e.getMessage());
    }
}
