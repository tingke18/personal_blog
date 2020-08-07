package com.tingke.admin.model;

public interface ResultCode {
    //是否正确
    public boolean success();
    //返回代码
    public int code();
    //返回信息
    public String message();

}
