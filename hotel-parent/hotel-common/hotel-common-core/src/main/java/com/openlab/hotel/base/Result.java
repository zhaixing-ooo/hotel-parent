package com.openlab.hotel.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Setter
@Getter
public final class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    private Result() {
    }

    // 成功的静态方法
    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage(HttpStatus.OK.getReasonPhrase());
        return result;
    }

    // 失败静态方法
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.PRECONDITION_FAILED.value());
        result.setMessage(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
        return result;
    }

    // 设置状态码
    public Result<T> code(int code) {
        this.setCode(code);
        return this;
    }

    // 设置提示信息
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    // 设置结果数据
    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }

}
