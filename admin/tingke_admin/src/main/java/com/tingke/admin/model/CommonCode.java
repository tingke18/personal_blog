package com.tingke.admin.model;

import lombok.ToString;

//枚举类，返回自定义数据
@ToString
public enum  CommonCode implements ResultCode{
    EMAIL_NAME_EXIST(false,10016,"邮箱或用户名已存在！"),
    EMAIL_FAIL(false,10015,"邮箱格式错误！"),
    PASSWORD_SAME(false,10014,"密码与之前相同！"),
    PASSWORD_DIFFERENCE(false,10013,"密码不一致！"),
    PASSWORD_REPRTITION(false,10012,"密码重复！"),
    SEND_EMAIL_ERROR(false,10011,"发送邮箱失败！"),
    UPLOAD_ERROR(false,10010,"上传失败！"),
    USER_PASSWORD_ERROR(false,10009,"用户名或密码错误！"),
    CODE_FAIL(false,10008,"验证码错误，请重新输入！"),
    CODE_REPETITION(false,10007,"请勿重复获取验证码！"),
    CODE_PAST(false,10006,"验证码过期！"),
    PHONE_IS_SUCCESS(false,10005,"请输入正确手机号！"),
    INVALID_PARAM(false,10004,"非法参数！"),
    METHOD_NOT_SUPPORT(false,10003,"请求方式不正确！"),
    UNAUTHENTICATED(false,10002,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10001,"权限不足，无权操作！"),
    FAIL(false,55555,"操作失败！"),
    SUCCESS(true,10000,"操作成功！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");

    private Boolean success;
    private int code;
    private String message;

    CommonCode(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
