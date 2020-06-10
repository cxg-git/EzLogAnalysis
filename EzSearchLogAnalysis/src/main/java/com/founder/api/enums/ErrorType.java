package com.founder.api.enums;

/**
 * @program: workspace
 * @description: 系统错误类型
 * @author: 刘宗强
 * @create: 2019-08-27 10:04
 **/
public enum ErrorType {
    //资源参数问题
    PARAM_INVALID(-1, "参数不合法"),
    NOT_FOUND(2, "不存在此资源"),
    ALREADY_EXISTS(3, "资源已经存在,不能重复"),
    PARAMS_NULL(3, "参数不能为空"),

    //身份方面问题
    REQUEST_OUT_OF_TIME(4, "请求过期"),
    AUTHENTICATION_FAILED(100, "身份认证失败"),
    REQUEST_METHOD_ERROR(101, "请求类型不支持"),
    OPERATION_SCOPE_FAILED(102, "不支持此操作"),

    //系统基本码
    ERROR(0, "系统异常"),
    SUCCESS(1, "成功");


    private int code;

    private String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
