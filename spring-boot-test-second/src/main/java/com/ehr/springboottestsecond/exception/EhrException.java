package com.ehr.springboottestsecond.exception;

/**
 * 描述: TODO
 */
public class EhrException extends RuntimeException {
    private final Integer code;
    private final String message;

    public EhrException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //还有一个更为简洁的方法和统一处理异常的方法一样，直接传入一个异常的枚举
    public EhrException(ExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
