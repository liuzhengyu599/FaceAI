package com.ayu.domain;

public enum ErrorCode {
    SERVICE_OK(10000, "服务正常"),
    GENDER(10001, "性别错误"),
    IDCARD(10002, "身份证错误"),
    EXIST(10003, "用户已存在"),
    NOTEXIST(10004, "用户不存在"),
    REGISTER(10005, "注册成功"),
    NOREGISTER(10006, "注册不成功"),
    LOGIN(10007, "登录成功"),
    NOLOGIN(10008, "登录失败");
    private int code;
    private String msg;

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

}
