package com.tingke.admin.model;

import lombok.Data;
import lombok.ToString;

//通过data返回可以让restController返回json数据
@Data
@ToString
public class ResponseResult {

    //操作是否成功
    boolean success;

    //操作代码
    int code;

    //提示信息
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

}
