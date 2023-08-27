package com.openlab.hotel.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * @Description 自定义异常类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Slf4j
@Getter
public class BadRequestException extends RuntimeException {
    private int code = HttpStatus.BAD_REQUEST.value();
    private final String message = HttpStatus.BAD_REQUEST.getReasonPhrase();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }
}
