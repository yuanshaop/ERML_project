package com.ehr.springboottestsecond.common;

import com.ehr.springboottestsecond.exception.ExceptionEnum;

/**
 * 描述: TODO
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    private static final int OK_CODE = 200;
    private static final String OK_MSG = "成功";

    public Result() {
        this(OK_CODE, OK_MSG);
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T>Result<T> success(){
        return new Result<>();
    }

    public static <T>Result<T> success(T t){
        Result<T> result = new Result<>();
        result.setData(t);
        return result;
    }

    public static <T>Result<T> error(Integer code,String msg){
        return new Result<>(code, msg);
    }

    public static <T>Result<T> error(ExceptionEnum ee){
        return new Result<>(ee.getCode(), ee.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
