package com.ehr.springboottestsecond.exception;

/**
 * 描述: TODO
 */
public enum ExceptionEnum {
    NEED_LOGIN(401, "用户未登录"),
    NOT_USERNAME(401,"用户名错误");
    Integer code;
    String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
